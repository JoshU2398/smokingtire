package learn.capstone.data;

import learn.capstone.data.mappers.ImageMapper;
import learn.capstone.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ImageDbRepo implements ImageRepo{

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Image> findAll() {
        String sql = "select * from images;";
        return template.query(sql, new ImageMapper());
    }

    @Override
    public Image findById(Integer id) {
        String sql = "select * from images where imageId = ?;";

        return template.query(sql, new ImageMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Image> findImagesByListing(Integer listingId) {
        String sql = "select * from images " +
                "inner join models on images.modelId = models.modelId " +
                "inner join makes on models.modelId = makes.modelId " +
                "inner join cars on makes.makeId = cars.makeId " +
                "inner join listings on cars.carId = listings.carId " +
                "where listingId = ?;";

        return template.query(sql, new ImageMapper(), listingId);
    }

    @Override
    public Image add(Image toAdd) {
        String sql = "insert into images (imageUrl, modelId) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toAdd.getImageUrl());
            ps.setInt(2, toAdd.getModelId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0){
            return null;
        }

        toAdd.setImageId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return toAdd;
    }

    @Override
    public Boolean update(Image toUpdate) {
        String sql = "update images set imageUrl = ?, modelId = ? where imageId = ?";

        return template.update(sql, toUpdate.getImageUrl(), toUpdate.getModelId(), toUpdate.getImageId()) > 0;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return template.update("delete from images where imageId = ?;", id) > 0;
    }
}
