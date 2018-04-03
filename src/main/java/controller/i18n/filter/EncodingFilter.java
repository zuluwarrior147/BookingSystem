package controller.i18n.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Zulu Warrior on 6/1/2017.
 */
public class EncodingFilter implements Filter {
    private static final String ENCODING = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
