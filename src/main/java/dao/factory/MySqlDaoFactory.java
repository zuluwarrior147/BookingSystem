package dao.factory;

import dao.abstraction.*;
import dao.datasource.PooledConnection;
import dao.exception.DaoException;
import dao.factory.connection.DaoConnection;
import dao.factory.connection.MySqlConnection;
import dao.impl.mysql.*;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDaoFactory extends DaoFactory {
    private final static String NULLABLE_CONNECTION =
            "Null pointer connection!";

    private final static String WRONG_TYPE_CONNECTION =
            "Wrong type connection!";

    private DataSource dataSource = PooledConnection.getInstance();

    public DaoConnection getConnection() {
        try{
        return new MySqlConnection(dataSource.getConnection());
        } catch(SQLException e) {
           throw new DaoException(e);
        }
    }

    public UserDao getUserDao(DaoConnection connection) {
        return new UserMySqlDao(getSqlConnection(connection));
    }

    public AccountDao getAccountDao(DaoConnection connection) {
        return new AccountMySqlDao(getSqlConnection(connection));
    }

    public CardDao getCardDao(DaoConnection connection) {
        return new CardMySqlDao(getSqlConnection(connection));
    }

    public PaymentDao getPaymentDao(DaoConnection connection) {
        return new PaymentMySqlDao(getSqlConnection(connection));
    }

    public RoleDao getRoleDao(DaoConnection connection) {
        return new RoleMySqlDao(getSqlConnection(connection));
    }

    private Connection getSqlConnection(DaoConnection connection) {
        checkDaoConnection(connection);
        return (Connection) connection.getNativeConnection();
    }

    private void checkDaoConnection(DaoConnection connection) {
        if(connection == null || connection.getNativeConnection() == null) {
            throw new DaoException(NULLABLE_CONNECTION);
        }
        if(! (connection instanceof MySqlConnection)) {
            throw new DaoException(WRONG_TYPE_CONNECTION);
        }
    }
}
