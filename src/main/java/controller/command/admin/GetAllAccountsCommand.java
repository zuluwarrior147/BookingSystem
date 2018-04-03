package controller.command.admin;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.Account;
import service.AccountService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Zulu Warrior on 6/7/2017.
 */
public class GetAllAccountsCommand implements ICommand {
    private final AccountService accountService = ServiceFactory.getAccountService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Account> accounts = accountService.findAllAccounts();

        request.setAttribute(Attributes.ACCOUNTS, accounts);

        return Views.ACCOUNTS_VIEW;
    }
}
