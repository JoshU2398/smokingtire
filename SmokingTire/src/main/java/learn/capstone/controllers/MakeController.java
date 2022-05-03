package learn.capstone.controllers;

import learn.capstone.domain.ListingService;
import learn.capstone.domain.MakeService;
import learn.capstone.models.Listing;
import learn.capstone.models.Make;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/makes")
public class MakeController {

    private final MakeService service;

    public MakeController(MakeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Make> findAll(){
        return service.findAll();
    }

}
