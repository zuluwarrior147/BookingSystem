package validating;

import controller.util.validator.AccountNumberValidator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Zulu Warrior on 6/9/2017.
 */
public class AccountNumberValidatorTest {

    @Test
    public void validateEmail() {
        AccountNumberValidator accountNumberValidator = new AccountNumberValidator();

        String rightAccountNumber = "123456789";

        String wrongAccountNumber = "&nwu%1!32cn;}|j";

        Assert.assertTrue(accountNumberValidator.isValid(rightAccountNumber));
        Assert.assertFalse(accountNumberValidator.isValid(wrongAccountNumber));
    }
}
