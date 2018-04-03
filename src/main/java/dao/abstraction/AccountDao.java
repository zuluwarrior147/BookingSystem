package dao.abstraction;

import entity.Account;
import entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public interface AccountDao extends GenericDao<Account, Long> {
    /**
     * Retrieves all accounts associated with certain user.
     *
     * @param user user to retrieve accounts related with him
     * @return list of retrieved accounts
     */
    List<Account> findByUser(User user);

    /**
     * Retrieves all accounts associated with certain account status.
     *
     * @param status status of account
     * @return list of retrieved accounts
     */
    List<Account> findByStatus(Account.Status status);

    /**
     * Increase account balance of certain amount.
     *
     * @param account account to increase
     * @param amount amount of increasing
     */
    void increaseBalance(Account account, BigDecimal amount);

    /**
     * Decrease account balance of certain amount.
     *
     * @param account account to decrease
     * @param amount amount of decreasing
     */
    void decreaseBalance(Account account, BigDecimal amount);

    /**
     * Updates certain account status.
     *
     * @param account account which status will be updated.
     * @param status new status of account to update
     */
    void updateAccountStatus(Account account, Account.Status status);

}
