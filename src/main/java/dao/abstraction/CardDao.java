package dao.abstraction;

import entity.Account;
import entity.Card;
import entity.User;

import java.util.List;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public interface CardDao extends GenericDao<Card, Long> {
    /**
     * Retrieves all cards associated with certain user.
     *
     * @param user user to retrieve cards
     * @return list of retrieved cards
     */
    List<Card> findByUser(User user);

    /**
     * Retrieves all cards associated with certain account.
     *
     * @param account account to retrieve cards
     * @return list of retrieved cards
     */
    List<Card> findByAccount(Account account);
}
