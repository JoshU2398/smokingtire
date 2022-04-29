package learn.capstone.data.mappers;

import learn.capstone.models.Make;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MakeMapper implements RowMapper<Make> {
    @Override
    public Make mapRow(ResultSet rs, int rowNum) throws SQLException {
        Make toReturn = new Make();
        toReturn.setMakeId(rs.getInt("makeId"));
        toReturn.setMakeName(rs.getString("makeName"));

        return toReturn;
    }
}
