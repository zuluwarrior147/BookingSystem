package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.PagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Views.LOGIN_VIEW;

/**
 * Created by Zulu Warrior on 6/2/2017.
 */
public class GetLoginCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        return LOGIN_VIEW;
    }
}
