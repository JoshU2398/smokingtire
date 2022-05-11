
package learn.capstone.data;

import learn.capstone.data.mappers.UserMapper;
import learn.capstone.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserDbRepo implements UserRepo {

    @Autowired
    JdbcTemplate template;

    @Override
    @Transactional
    public AppUser findByUsername(String username) {

        String sql = "select userId, username, password from users where username = ?";

        return template.query(
                sql,
                new UserMapper(findRolesByUsername(username)),
                username).stream().findAny().orElse(null);
    }

    @Override
    public AppUser findById(Integer userId) {
        String sql = "select userId, username, password from users where userId = ?";

        return template.query(
                sql,
                new UserMapper(findRolesById(userId)),
                userId).stream().findAny().orElse(null);
    }


    private Set<String> findRolesByUsername(String username ){

        String sql = "SELECT roleName " +
                "FROM users u " +
                "inner join userroles ur on ur.userId = u.userId " +
                "inner join roles r on ur.roleId = r.roleId " +
                "where username = ?";

        return template.query(
                sql,
                (rowData, rowNum) -> rowData.getString( "roleName"),
                username).stream().collect(Collectors.toSet());
    }

    private Set<String> findRolesById(Integer userId) {
        String sql = "SELECT roleName " +
                "FROM users u " +
                "inner join userroles ur on ur.userId = u.userId " +
                "inner join roles r on ur.roleId = r.roleId " +
                "where u.userId = ?";

        return template.query(
                sql,
                (rowData, rowNum) -> rowData.getString( "roleName"),
                userId).stream().collect(Collectors.toSet());
    }

    @Override
    public AppUser add(AppUser toAdd) {
        String sql = "insert into users (username, password) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toAdd.getUsername());
            ps.setString(2, toAdd.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        toAdd.setUserId(keyHolder.getKey().intValue());
        setUserRole(toAdd.getUserId());

        return toAdd;
    }

    @Override
    public boolean remove(Integer userId) {
        template.update("delete from listings where userId = ?;", userId);
        template.update("delete from userroles where userId = ?;", userId);
        return template.update("delete from users where userId = ?;", userId) > 0;
    }

    @Override
    public boolean edit(AppUser updated) {
        String sql = "update users set username = ?, password = ? where userId = ?;";

        return template.update(sql, updated.getUsername(), updated.getPassword(), updated.getUserId()) > 0;
    }

    private void setUserRole(Integer userId) {
        String sql = "insert into userroles (userId, roleId) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setInt(2, 1);
            return ps;
        }, keyHolder);
    }

}

