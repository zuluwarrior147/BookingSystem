package dao.impl.mysql.converter;

import entity.Role;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Zulu Warrior on 5/22/2017.
 */
public class UserDtoConverter implements DtoConverter<User>{
    private final static String ID_FIELD = "user_id";
    private final static String FIRST_NAME_FIELD = "first_name";
    private final static String LAST_NAME_FIELD = "last_name";
    private final static String EMAIL_FIELD = "email";
    private final static String PASSHASH_FIELD = "passhash";
    private final static String PHONE_NUMBER_FIELD = "phone_number";

    private final DtoConverter<Role> roleConverter;

    public UserDtoConverter() {
    this(new RoleDtoConverter());
    }

    public UserDtoConverter(DtoConverter<Role> roleConverter) {
        this.roleConverter = roleConverter;
    }


    @Override
    public User convertToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException{

        Role tempRole = roleConverter.convertToObject(resultSet);

        User user = User.newBuilder()
                .setId(resultSet.getInt(
                        tablePrefix + ID_FIELD))
                .setRole(tempRole)
                .setFirstName(resultSet.getString(
                        tablePrefix + FIRST_NAME_FIELD))
                .setLastName(resultSet.getString(
                        tablePrefix + LAST_NAME_FIELD))
                .setEmail(resultSet.getString(
                        tablePrefix + EMAIL_FIELD))
                .setPassword(resultSet.getString(
                        tablePrefix + PASSHASH_FIELD
                ))
                .setPhoneNumber(resultSet.getString(
                        tablePrefix + PHONE_NUMBER_FIELD
                ))
                .build();

        return user;
    }
}
