package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import controller.util.validator.EmailValidator;
import controller.util.validator.NameValidator;
import controller.util.validator.PasswordValidator;
import controller.util.validator.PhoneValidator;
import dao.util.card.CardPassGenerator;
import entity.Account;
import entity.Card;
import entity.User;
import service.AccountService;
import service.CardService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zulu Warrior on 6/3/2017.
 */
public class PostSignupCommand implements ICommand {
    private final static String EMAIL_PARAM = "email";
    private final static String PASSWORD_PARAM = "password";
    private final static String FIRSTNAME_PARAM = "firstname";
    private final static String LASTNAME_PARAM = "lastname";
    private final static String PHONE_PARAM = "phone";

    private final static String INVALID_FIRSTNAME_KEY = "invalid.firstname";
    private final static String INVALID_LASTNAME_KEY = "invalid.lastname";
    private final static String USER_ALREADY_EXISTS = "user.exists";

    private final static String ACCOUNT_CREATED =
            "Created profile for user with email - ";

    private final UserService userService = ServiceFactory.getUserService();
    private final AccountService accountService = ServiceFactory.getAccountService();
    private final CardService cardService = ServiceFactory.getCardService();

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
            User createdUser = userService.createUser(userDto);
            Account userAccount = createNewAccount(createdUser);
            createNewCard(userAccount, createdUser);

            addUserToSession(request.getSession(), createdUser);

            Util.redirectTo(request, response, PagesPaths.HOME_PATH);

            return REDIRECTED;
        }

        addInvalidDataToRequest(request, userDto, errors);

        return Views.SIGNUP_VIEW;


    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(EMAIL_PARAM))
                .setPassword(request.getParameter(PASSWORD_PARAM))
                .setFirstName(request.getParameter(FIRSTNAME_PARAM))
                .setLastName(request.getParameter(LASTNAME_PARAM))
                .setPhoneNumber(request.getParameter(PHONE_PARAM))
                .build();
    }

    private Account createNewAccount(User user) {
        Account tempAccount = new Account(Account.DEFAULT_NUMBER,
                user,
                new BigDecimal(Account.DEFAULT_BALANCE),
                Account.Status.PENDING);

        Account userAccount = accountService.createAccount(tempAccount);

        return userAccount;
    }

    private Card createNewCard(Account account, User user) {
        Card tempCard = Card.newBuilder()
                .setCardNumber(Card.DEFAULT_NUMBER)
                .setAccount(account)
                .setCardHolder(user)
                .setCvv(CardPassGenerator.getRandomCvv())
                .setPin(CardPassGenerator.getRandomPin())
                .setExpireDate(new Date())
                .setType(Card.CardType.VISA)
                .build();

        Card createdCard = cardService.createCard(tempCard);

        return createdCard;
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new EmailValidator(), user.getEmail(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);
        Util.validateField(new PhoneValidator(), user.getPhoneNumber(), errors);

        NameValidator nameValidator = new NameValidator(INVALID_FIRSTNAME_KEY);
        Util.validateField(nameValidator, user.getFirstName(), errors);

        NameValidator surnameValidator = new NameValidator(INVALID_LASTNAME_KEY);
        Util.validateField(surnameValidator, user.getLastName(), errors);

        if(errors.isEmpty() && userService.isUserExists(user)) {
            errors.add(USER_ALREADY_EXISTS);
        }

        return errors;
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         User user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER, user);
        request.setAttribute(Attributes.ERRORS, errors);
    }

    private void addUserToSession(HttpSession session, User user)
            throws IOException {
        session.setAttribute(Attributes.USER, user);
    }
}
