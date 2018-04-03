package dao.impl.mysql;

import dao.abstraction.UserDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.UserDtoConverter;
import entity.User;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public class UserMySqlDao implements UserDao {
    private final static String SELECT_ALL =
            "SELECT users.user_id, users.user_type, " +
                    "users.first_name, users.last_name, " +
                    "users.email, users.passhash, " +
                    "users.phone_number," +
                    "roles.role_id, roles.role_name " +
                    "FROM users JOIN roles " +
                    "ON user_type = role_id ";

    private final static String WHERE_ID =
            "WHERE users.user_id = ? ";

    private final static String WHERE_EMAIL =
            "WHERE users.email = ? ";

    private final static String INSERT =
            "INSERT INTO users" +
                    "(user_type, first_name, last_name, " +
                    "email, passhash, phone_number) " +
                    "VALUES(?, ?, ?, ?, ?, ?) ";

    private final static String UPDATE =
            "UPDATE users SET " +
                    "user_type = ?, first_name = ?, " +
                    "last_name = ?, email = ?, " +
                    "passhash = ?, phone_number = ? ";

    private final static String DELETE =
            "DELETE FROM users ";


    private final DefaultDaoImpl<User> defaultDao;

    public UserMySqlDao(Connection connection) {
        this(connection, new UserDtoConverter());
    }

    public UserMySqlDao(Connection connection,
                        DtoConverter<User> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    public UserMySqlDao(DefaultDaoImpl<User> defaultDao) {
        this.defaultDao = defaultDao;
    }

    @Override
    public Optional<User> findOne(Integer id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return defaultDao.findOne(SELECT_ALL + WHERE_EMAIL, email);
    }

    @Override
    public List<User> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public User insert(User obj) {
        Objects.requireNonNull(obj);

        int id = (int)defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword(),
                obj.getPhoneNumber()
        );

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(User obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword(),
                obj.getPhoneNumber(),
                obj.getId()
        );
    }

    @Override
    public void delete(Integer id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public boolean existByEmail(String email) {
        return findOneByEmail(email).isPresent();
    }
}
