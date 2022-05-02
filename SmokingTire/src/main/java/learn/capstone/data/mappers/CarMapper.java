package learn.capstone.data.mappers;

import learn.capstone.models.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car toReturn = new Car();
                toReturn.setCarId(rs.getInt("carId"));
                toReturn.setHorsepower(rs.getInt("horsepower"));
                toReturn.setDrivetrain(rs.getString("drivetrain"));
                toReturn.setChassis(rs.getString("chassis"));
                toReturn.setTransmission(rs.getString("transmission"));

        return toReturn;
    }
}
