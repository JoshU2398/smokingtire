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

        String sql = "select carId, horsepower, drivetrain, chassis, transmission, modelId;";

        return template.query(sql, new CarMapper());
    }

    @Override
    public Car findByModelId(Integer modelId) {
        final String sql = "select c.carId, c.horsepower, c.drivetrain, c.chassis, c.transmission, c.modelId "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join models mo on mo.modelId = c.modelId "
                + "inner join makes m on m.makeId = mo.makeId "
                + "where mo.modelId = ?;";

        Car result = template.query(sql, new CarMapper(), modelId).stream()
                .findFirst().orElse(null);

        if (result != null){
            addModel(result);
        }

        return result;
    }

    private void addMakeName(Model model){
        final String sql = "select m.makeId, m.makeName "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join models mo on mo.modelId = c.modelId "
                + "inner join makes m on m.makeId = mo.makeId "
                + "where mo.modelId = ?;";

        Make make = template.query(sql, new MakeMapper(), model.getModelId()).stream()
                .findFirst().orElse(null);

        model.setMakeName(make.getMakeName());
    }

    private void addModel(Car car){
        final String sql = "select mo.modelId, mo.modelName, mo.modelYear, mo.makeId "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join models mo on mo.modelId = c.modelId "
                + "inner join makes m on m.makeId = mo.makeId "
                + "where c.carId = ?;";

        Model model = template.query(sql, new ModelMapper(), car.getCarId()).stream()
                .findFirst().orElse(null);
        addMakeName(model);
        car.setModel(model);
    }
}
