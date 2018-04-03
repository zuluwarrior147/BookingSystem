package dao.impl.mysql.converter;

import entity.Account;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Zulu Warrior on 5/22/2017.
 */
public class AccountDtoConverter implements DtoConverter<Account>{
    private final static String ACCOUNT_NUMBER_FIELD = "account_number";
    private final static String BALANCE_FIELD = "balance";
    private final static String STATUS_FIELD = "status";
    private final DtoConverter<User> userConverter;

    public AccountDtoConverter() {
        this(new UserDtoConverter());
    }

    public AccountDtoConverter(DtoConverter<User> userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public Account convertToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {

       User accountHolder = userConverter.
               convertToObject(resultSet);

       Account account = new Account(
               resultSet.getLong(
                       tablePrefix + ACCOUNT_NUMBER_FIELD),
               accountHolder,
               resultSet.getBigDecimal(tablePrefix + BALANCE_FIELD),
               Account.Status.valueOf(resultSet.getString(tablePrefix + STATUS_FIELD))
       );

        return account;
    }
}
