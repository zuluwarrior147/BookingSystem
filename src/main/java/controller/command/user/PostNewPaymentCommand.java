package controller.command.user;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.PagesPaths;
import controller.util.constants.Views;
import controller.util.validator.AccountNumberValidator;
import controller.util.validator.AmountValidator;
import entity.Account;
import entity.Payment;
import entity.User;
import service.AccountService;
import service.PaymentService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class PostNewPaymentCommand implements ICommand {
    private final static String ACCOUNT_FROM_PARAM = "from";
    private final static String ACCOUNT_TO_PARAM = "to";
    private final static String AMOUNT_PARAM = "amount";
    private final static String NO_SUCH_ACCOUNT = "account.not.exist";
    private final static String NOT_ENOUGH_MONEY = "account.insufficient.funds";
    private final static String TRANSACTION_COMPLETE = "payment.success";

    private final AccountService accountService = ServiceFactory.getAccountService();
    private final PaymentService paymentService = ServiceFactory.getPaymentService();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = validateAllData(request);

        if (errors.isEmpty()) {
            Payment payment = createPayment(request);
            paymentService.createPayment(payment);

            List<String> messages = new ArrayList<>();
            messages.add(TRANSACTION_COMPLETE);

            addMessageDataToRequest(request, Attributes.MESSAGES, messages);

            addAccountListToRequest(request);

            return Views.NEW_PAYMENT_VIEW;
        }

        addMessageDataToRequest(request, Attributes.ERRORS, errors);

        addAccountListToRequest(request);

        return Views.NEW_PAYMENT_VIEW;
    }

    private List<String> validateAllData(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        validateDataFromRequest(request, errors);

        Optional<Account> accountFromOptional = Optional.empty();
        Optional<Account> accountToOptional = Optional.empty();

        if (errors.isEmpty()) {
            accountFromOptional = getOptAccountFromRequest(request, ACCOUNT_FROM_PARAM);
            accountToOptional = getOptAccountFromRequest(request, ACCOUNT_TO_PARAM);
        }

        if (!accountFromOptional.isPresent() ||
                !accountToOptional.isPresent()) {
            errors.add(NO_SUCH_ACCOUNT);
            return errors;
        }

        validateAccountBalance(request, accountFromOptional.get(), errors);

        return errors;
    }

    private void validateDataFromRequest(HttpServletRequest request,
                                         List<String> errors) {

        Util.validateField(new AccountNumberValidator(),
                request.getParameter(ACCOUNT_FROM_PARAM), errors);

        Util.validateField(new AccountNumberValidator(),
                request.getParameter(ACCOUNT_TO_PARAM), errors);

        Util.validateField(new AmountValidator(),
                request.getParameter(AMOUNT_PARAM), errors);
    }

    private void validateAccountBalance(HttpServletRequest request,
                                        Account account, List<String> errors) {
        BigDecimal paymentAmount = new BigDecimal(request.getParameter(AMOUNT_PARAM));

        BigDecimal accountBalance = account.getBalance();

        if (accountBalance.compareTo(paymentAmount) < 0) {
            errors.add(NOT_ENOUGH_MONEY);
        }
    }

    private Optional<Account> getOptAccountFromRequest(HttpServletRequest request,
                                                       String accountParameter) {

        long accountNumber = Long.valueOf(request.getParameter(accountParameter));

        return accountService.findAccountByNumber(accountNumber);
    }

    private Account getAccount(HttpServletRequest request,
                               String accountParameter) {
        return getOptAccountFromRequest(request, accountParameter).get();
    }

    private Payment createPayment(HttpServletRequest request) {
        Account accountFrom = getAccount(request, ACCOUNT_FROM_PARAM);
        Account accountTo = getAccount(request, ACCOUNT_TO_PARAM);
        BigDecimal amount = new BigDecimal(request.getParameter(AMOUNT_PARAM));

        Payment payment = Payment.newBuilder()
                .setAccountFrom(accountFrom)
                .setAccountTo(accountTo)
                .setAmount(amount)
                .setDate(new Date())
                .build();

        return payment;
    }

    private void addMessageDataToRequest(HttpServletRequest request,
                                         String attribute,
                                         List<String> messages) {
        request.setAttribute(attribute, messages);
    }

    private void addAccountListToRequest(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.USER);

        List<Account> accounts = accountService.findAllByUser(user);

        request.setAttribute(Attributes.ACCOUNTS, accounts);
    }

}
