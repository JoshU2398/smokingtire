package learn.capstone.controllers;

import learn.capstone.domain.ModelService;
import learn.capstone.models.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/models")
public class ModelController {

    private final ModelService service;

    public ModelController(ModelService service) {
        this.service = service;
    }

    @GetMapping
    public List<Model> findAll(){
        return service.findAll();
    }

    @GetMapping("/findByMake/{makeId}")
    public List<Model> findAllByMake(@PathVariable Integer makeId) {
        return service.findAllByMake(makeId);
    }
    
}
