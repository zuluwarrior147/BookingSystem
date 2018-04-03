package hashing;

import dao.util.hashing.PasswordStorage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Zulu Warrior on 6/9/2017.
 */
public class HashingTest {

    @Test
    public void checkingHash() {
        String password = "testPassword";

        String passwordHash = PasswordStorage.getSecurePassword(password);

        Assert.assertTrue(PasswordStorage.checkSecurePassword(password, passwordHash));
    }
}
