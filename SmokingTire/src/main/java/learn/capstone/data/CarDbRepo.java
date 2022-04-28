package learn.capstone.data;

import learn.capstone.data.mappers.CarMapper;
import learn.capstone.models.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CarDbRepo implements CarRepo{

    private final JdbcTemplate jdbcTemplate;

    public CarDbRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Car> findAll() {

        String sql = "select carId, horsepower, drivetrain, chassis, transmission, makeId;";

        return jdbcTemplate.query(sql, new CarMapper());
    }
}
