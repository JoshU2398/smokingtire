package learn.capstone.controllers;

import learn.capstone.domain.CarService;
import learn.capstone.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    CarService service;

    @GetMapping("/{modelId}")
    public Car findByModelId(@PathVariable Integer modelId){
        return service.findByModelId(modelId);
    }
}
