package learn.capstone.data;

import learn.capstone.data.mappers.ModelMapper;
import learn.capstone.models.Model;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

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

}
