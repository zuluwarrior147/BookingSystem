package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.Account;
import entity.User;
import service.AccountService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Zulu Warrior on 6/7/2017.
 */
public class GetReplenishCommand implements ICommand {
    private final AccountService accountService = ServiceFactory.getAccountService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserFromSession(request.getSession());

        List<Account> accounts = accountService.findAllByUser(user);

        request.setAttribute(Attributes.ACCOUNTS, accounts);

        return Views.REPLENISH_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }
}
