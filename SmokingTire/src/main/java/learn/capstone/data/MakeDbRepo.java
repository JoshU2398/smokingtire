package learn.capstone.data;

import learn.capstone.data.mappers.MakeMapper;
import learn.capstone.models.Make;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class MakeDbRepo implements MakeRepo {

    private final JdbcTemplate template;

    public MakeDbRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Make> findAll() {
        String sql = "select makeId, makeName, modelId from makes;";

        return template.query(sql, new MakeMapper()).stream().collect(Collectors.toList());
    }

}
