package learn.capstone.data;

import learn.capstone.data.mappers.CarMapper;
import learn.capstone.data.mappers.MakeMapper;
import learn.capstone.data.mappers.ModelMapper;
import learn.capstone.models.Car;
import learn.capstone.models.Make;
import learn.capstone.models.Model;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CarDbRepo implements CarRepo{

    private final JdbcTemplate template;

    public CarDbRepo(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    @Override
    public List<Car> findAll() {

        String sql = "select carId, horsepower, drivetrain, chassis, transmission, makeId;";

        return template.query(sql, new CarMapper());
    }

    @Override
    public Car findByModelId(Integer modelId) {
        final String sql = "select c.carId, c.horsepower, c.drivetrain, c.chassis, c.transmission, c.makeId "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "inner join models mo on mo.modelId = m.modelId "
                + "where mo.modelId = ?;";

        Car result = template.query(sql, new CarMapper(), modelId).stream()
                .findFirst().orElse(null);

        if (result != null){
            addMake(result);
        }

        return result;
    }

    private void addMake(Car car){
        final String sql = "select m.makeId, m.makeName, m.modelId "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "where c.carId = ?;";

        Make make = template.query(sql, new MakeMapper(), car.getCarId()).stream()
                .findFirst().orElse(null);

        addModel(make);
        car.setMake(make);
    }

    private void addModel(Make make){
        final String sql = "select mo.modelId, mo.modelName, mo.modelYear "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "inner join models mo on mo.modelId = m.modelId "
                + "where m.makeId = ?;";

        Model model = template.query(sql, new ModelMapper(), make.getMakeId()).stream()
                .findFirst().orElse(null);
        make.setModel(model);
    }
}
