package controller.command.admin;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import entity.Account;
import service.AccountService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ConfirmAccountCommand implements ICommand{
    private final static String ACCOUNT_PARAM = "account";
    private final static String ACCOUNT_CONFIRMED = "account.confirmed";

    private final AccountService accountService = ServiceFactory.getAccountService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<Account> account = getAccountFromRequest(request);

        if (account.isPresent()) {
            confirmAccount(account.get());
            addSuccessMessageToRequest(request);

            addAccountsListToRequest(request);

            return Views.ACCOUNTS_VIEW;
        }

        Util.redirectTo(request, response, PagesPaths.HOME_PATH);
        return REDIRECTED;
    }

    private Optional<Account> getAccountFromRequest(HttpServletRequest request) {
        long accountNumber = Long.valueOf(request.getParameter(ACCOUNT_PARAM));
        return accountService.findAccountByNumber(accountNumber);
    }

    private void confirmAccount(Account account) {
        accountService.updateAccountStatus(account, Account.Status.ACTIVE);
    }

    private void addSuccessMessageToRequest(HttpServletRequest request) {
        List<String> messages = new ArrayList<>();
        messages.add(ACCOUNT_CONFIRMED);
        request.setAttribute(Attributes.MESSAGES, messages);
    }

    private void addAccountsListToRequest(HttpServletRequest request) {
        List<Account> accounts = accountService.findAllAccounts();
        request.setAttribute(Attributes.ACCOUNTS, accounts);
    }
}

