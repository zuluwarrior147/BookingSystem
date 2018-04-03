package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.PagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Zulu Warrior on 6/4/2017.
 */
public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        Util.redirectTo(request, response, PagesPaths.LOGIN_PATH);
        return REDIRECTED;
    }
}
