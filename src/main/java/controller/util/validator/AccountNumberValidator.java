package controller.util.validator;

import javax.print.DocFlavor;

/**
 * Created by Zulu Warrior on 6/6/2017.
 */
public class AccountNumberValidator extends RegexValidator {
    private final static int MAX_LENGTH = 20;
    private final static String ACCOUNT_REGEX = "^\\d{9}$";
    private final static String INVALID_ACCOUNT_NUMBER = "invalid.account.number";

    public AccountNumberValidator() {
        super(ACCOUNT_REGEX, MAX_LENGTH, INVALID_ACCOUNT_NUMBER);
    }
}
