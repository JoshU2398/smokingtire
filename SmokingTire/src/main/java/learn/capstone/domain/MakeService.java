package learn.capstone.domain;

import learn.capstone.data.MakeRepo;
import learn.capstone.models.Make;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MakeService {

    @Autowired
    MakeRepo repo;

    public List<Make> findAll(){
        return repo.findAll();
    }
}
