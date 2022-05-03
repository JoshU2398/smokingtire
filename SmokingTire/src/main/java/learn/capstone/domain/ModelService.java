package learn.capstone.domain;

import learn.capstone.data.ModelRepo;
import learn.capstone.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    ModelRepo repo;

    public List<Model> findAll(){
        return repo.findAll();
    }

    public List<Model> findAllByMake(Integer makeId) {
        return repo.findAllByMake(makeId);
    }
}
