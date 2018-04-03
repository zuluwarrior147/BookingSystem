package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import controller.util.validator.EmailValidator;
import controller.util.validator.PasswordValidator;
import entity.User;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Zulu Warrior on 6/3/2017.
 */
public class PostLoginCommand implements ICommand {
    private final static String EMAIL_PARAM = "email";
    private final static String PASSWORD_PARAM = "password";
    private final static String INVALID_CREDENTIALS = "invalid.credentials";

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, PagesPaths.HOME_PATH);
            return REDIRECTED;
        }

        User userDto = getDataFromRequest(request);

        List<String> errors = validateData(userDto);

        if(errors.isEmpty()) {
            User user = loadUserFromDatabase(userDto.getEmail());
            addUserToSession(request.getSession(), user);

            Util.redirectTo(request, response, PagesPaths.HOME_PATH);

            return REDIRECTED;
        }

        addInvalidDataToRequest(request, userDto, errors);

        return Views.LOGIN_VIEW;
    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(EMAIL_PARAM))
                .setPassword(request.getParameter(PASSWORD_PARAM))
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new EmailValidator(), user.getEmail(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);

        /* Check if entered password matches with user password only in case,
            when email and password is valid
        */
        if(errors.isEmpty() && !userService.
                isCredentialsValid(user.getEmail(), user.getPassword())) {
            errors.add(INVALID_CREDENTIALS);
        }

        return errors;
    }

    private User loadUserFromDatabase(String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.orElseThrow(IllegalStateException::new);
    }

    private void addUserToSession(HttpSession session, User user)
            throws IOException {
        session.setAttribute(Attributes.USER, user);
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         User user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER, user);
        request.setAttribute(Attributes.ERRORS, errors);
    }

}
