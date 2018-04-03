package controller.filter;

import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import entity.User;

import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Zulu Warrior on 6/3/2017.
 */
public class AuthorizationFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AuthorizationFilter.class);
    private final static String ACCESS_DENIED = "Access denied for page: ";

    private final static int USER_ROLE_ID = 1;
    private final static int ADMIN_ROLE_ID = 2;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(!isUserLoggedIn(request)) {
            Util.redirectTo(
                    request,
                    (HttpServletResponse) servletResponse,
                    PagesPaths.LOGIN_PATH
            );
            logInfoAboutAccessDenied(request.getRequestURI());
            return;
        }

        User user = getUserFromSession(request.getSession());

        if(isUserRoleInvalidForRequestedPage(request, user)) {
            Util.redirectTo(
                    request,
                    (HttpServletResponse) servletResponse,
                    PagesPaths.HOME_PATH
            );
            logInfoAboutAccessDenied(request.getRequestURI());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute(Attributes.USER) != null;
    }

    private User getUserFromSession(HttpSession session) {
        return (User)session.getAttribute(Attributes.USER);
    }

    private boolean isUserRoleInvalidForRequestedPage(HttpServletRequest request,
                                                      User user) {
        return (isUserPage(request) && user.getRole().getId() != USER_ROLE_ID) ||
                (isAdminPage(request) && user.getRole().getId() != ADMIN_ROLE_ID);
    }

    private boolean isUserPage(HttpServletRequest request) {
        return request
                .getRequestURI()
                .startsWith(PagesPaths.SITE_PREFIX + PagesPaths.USER_PREFIX);
    }

    private boolean isAdminPage(HttpServletRequest request) {
        return request
                .getRequestURI()
                .startsWith(PagesPaths.SITE_PREFIX + PagesPaths.ADMIN_PREFIX);
    }

    private void logInfoAboutAccessDenied(String uri) {
        logger.info(ACCESS_DENIED + uri);
    }
}
