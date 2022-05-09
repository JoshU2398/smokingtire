package learn.capstone.data.mappers;

import learn.capstone.models.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
        Image result = new Image();
        result.setImageId(rs.getInt("imageId"));
        result.setImageUrl(rs.getString("imageUrl"));
        result.setModelId(rs.getInt("modelId"));

        return result;
    }
}
