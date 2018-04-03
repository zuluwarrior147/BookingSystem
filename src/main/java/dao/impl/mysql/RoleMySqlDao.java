package dao.impl.mysql;

import dao.abstraction.RoleDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.RoleDtoConverter;
import entity.Role;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Zulu Warrior on 5/22/2017.
 */
public class RoleMySqlDao implements RoleDao {
    private final static String SELECT_ALL =
            "SELECT * FROM roles ";

    private final static String INSERT =
            "INSERT INTO roles (role_name) " +
                    "VALUES(?);";

    private final static String UPDATE =
            "UPDATE roles SET role_name = ? ";

    private final static String DELETE =
            "DELETE FROM roles " ;

    private final static String WHERE_ID =
            "WHERE role_id = ? ";


    private final DefaultDaoImpl<Role> defaultDao;

    public RoleMySqlDao(Connection connection) {
        this(connection, new RoleDtoConverter());
    }

    public RoleMySqlDao(Connection connection,
                        DtoConverter<Role> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    public RoleMySqlDao(DefaultDaoImpl<Role> defaultDao) {
        this.defaultDao = defaultDao;
    }

    @Override
    public Optional<Role> findOne(Integer id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Role> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public Role insert(Role obj) {
        Objects.requireNonNull(obj);

        int id = (int)defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getName()
        );

        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Role obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
                obj.getId()
        );
    }

    @Override
    public void delete(Integer id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }
}
