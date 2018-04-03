package dao.util.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Zulu Warrior on 5/26/2017.
 */
public class PasswordStorage {

    public static String getSecurePassword(String passwordToHash)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordToHash.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean checkSecurePassword(String passwordToCheck,
                                             String passwordHash) {

        String checkPassHash = getSecurePassword(passwordToCheck);

        return passwordHash.equals(checkPassHash);
    }
}
