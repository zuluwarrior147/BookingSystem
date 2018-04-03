package controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Views.HOME_VIEW;

/**
 * Created by Zulu Warrior on 6/2/2017.
 */
public class HomeCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return HOME_VIEW;
    }
}
