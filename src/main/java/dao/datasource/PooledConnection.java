package dao.datasource;

/**
 * Created by Zulu Warrior on 5/20/2017.
 */

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class PooledConnection {
    private static final String DB_BUNDLE = "database";
    private static final String DB_URL = "url";
    private static final String DB_DRIVER = "driver";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    private static class Singleton{
        static final DataSource INSTANCE = initDataSource();
    }

    public static DataSource getInstance(){
        return Singleton.INSTANCE;
    }

    private static DataSource initDataSource(){
        ResourceBundle bundle = ResourceBundle.getBundle(DB_BUNDLE);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(bundle.getString(DB_DRIVER));
        dataSource.setUrl(bundle.getString(DB_URL));
        dataSource.setUsername(bundle.getString(DB_USER));
        dataSource.setPassword(bundle.getString(DB_PASSWORD));
        return dataSource;
    }
}
