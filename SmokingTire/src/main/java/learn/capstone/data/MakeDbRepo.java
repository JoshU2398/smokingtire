package learn.capstone.data;

import learn.capstone.data.mappers.MakeMapper;
import learn.capstone.data.mappers.ModelMapper;
import learn.capstone.models.Make;
import learn.capstone.models.Model;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public class MakeDbRepo implements MakeRepo {

    private final JdbcTemplate template;

    public MakeDbRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Make> findAll() {
        String sql = "select makeId, makeName, modelId from makes;";

        List<Make> result = template.query(sql, new MakeMapper()).stream().collect(Collectors.toList());

        if(result.size() != 0){
            addModel(result);
        }
        return result;
    }

    private void addModel(List<Make> makes){
        final String sql = "select mo.modelId, mo.modelName, mo.modelYear "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "inner join models mo on mo.modelId = m.modelId "
                + "where m.makeId = ?;";

        for (Make m : makes) {
            Model model = template.query(sql, new ModelMapper(), m.getMakeId()).stream()
                    .findFirst().orElse(null);
            m.setModel(model);
        }
    }

}
