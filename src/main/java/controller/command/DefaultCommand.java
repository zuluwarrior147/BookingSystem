package controller.command;

import controller.util.Util;
import controller.util.constants.PagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Zulu Warrior on 6/2/2017.
 */
public class DefaultCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Util.redirectTo(request, response, PagesPaths.HOME_PATH);
        //request.getRequestDispatcher(Views.HOME_VIEW).forward(request, response);
        return REDIRECTED;
    }
}
