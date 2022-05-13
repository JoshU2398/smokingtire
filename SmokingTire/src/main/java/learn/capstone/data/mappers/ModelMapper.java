package learn.capstone.data.mappers;

import learn.capstone.models.Model;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelMapper implements RowMapper<Model> {
    @Override
    public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
        Model toReturn = new Model();
        toReturn.setModelId(rs.getInt("modelId"));
        toReturn.setModelName(rs.getString("modelName"));
        toReturn.setModelYear(rs.getInt("modelYear"));
        toReturn.setMakeId(rs.getInt("makeId"));
        return toReturn;

    }
}
