package learn.capstone.data;

import learn.capstone.models.Car;

import java.util.List;

public interface CarRepo {
    List<Car> findAll();
}
