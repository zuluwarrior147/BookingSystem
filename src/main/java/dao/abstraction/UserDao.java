package dao.abstraction;

import entity.User;

import java.util.Optional;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public interface UserDao extends GenericDao<User, Integer>{

    /**
     * Retrieve user from database identified by email.
     * @param email identifier of user
     * @return optional, which contains retrieved object or null
     */
    Optional<User> findOneByEmail(String email);

    /**
     * Check if user exists in database.
     *
     * @param email user's identifier
     * @return {@code true} if exists else {@code false}
     */
    boolean existByEmail(String email);
}
