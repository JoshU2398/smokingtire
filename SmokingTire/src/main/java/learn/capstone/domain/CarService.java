package learn.capstone.domain;

import learn.capstone.data.CarRepo;
import learn.capstone.models.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepo repo;

    public CarService(CarRepo repo){
        this.repo = repo;
    }

    public List<Car> findAll(){
        return repo.findAll();
    }

}
