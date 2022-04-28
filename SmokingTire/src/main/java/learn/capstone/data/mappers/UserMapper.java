package learn.capstone.data.mappers;

import learn.capstone.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class UserMapper implements RowMapper<AppUser> {

    Set<String> roles;

    public UserMapper(Set<String> roles){
        this.roles = roles;
    }

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser toReturn = new AppUser(
                rs.getInt("userId"),
                rs.getString("username"),
                rs.getString("password"),
                roles);

        return toReturn;
    }

}
