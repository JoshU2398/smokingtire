package learn.capstone.data;

import learn.capstone.data.mappers.MakeMapper;
import learn.capstone.data.mappers.ModelMapper;
import learn.capstone.models.Make;
import learn.capstone.models.Model;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class ModelDbRepo implements ModelRepo {

    private final JdbcTemplate template;

    public ModelDbRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Model> findAll() {
        String sql = "select modelId, modelName, modelYear from models;";

        return template.query(sql, new ModelMapper()).stream().collect(Collectors.toList());
    }

    @Override
    public List<Model> findAllByMake(Integer makeId) {
        List<Model> result;

        String sql = "select mo.modelId, mo.modelName, mo.modelYear, mo.makeId "
                + "from models mo "
                + "inner join makes m on mo.makeId = m.makeId "
                + "where mo.makeId = ?;";

        result = template.query(sql, new ModelMapper(), makeId).stream().collect(Collectors.toList());

        if (result.size() != 0){
            addMake(result);
        }

        return result;
    }

    private void addMake(List<Model> models){
        final String sql = "select m.makeId, m.makeName "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join models mo on mo.modelId = c.modelId "
                + "inner join makes m on m.makeId = mo.makeId "
                + "where mo.modelId = ?;";

        for (Model m : models) {
            Make make = template.query(sql, new MakeMapper(), m.getModelId()).stream()
                    .findFirst().orElse(null);
            m.setMakeName(make.getMakeName());
        }
    }

}
