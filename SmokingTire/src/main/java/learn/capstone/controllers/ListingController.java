package learn.capstone.controllers;


import learn.capstone.domain.ListingService;
import learn.capstone.domain.Result;
import learn.capstone.models.Listing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService service;

    public ListingController(ListingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Listing> findAllAvailableListings(){
        return service.findAllAvailableListings();
    }


    @GetMapping("/{userId}")
    public List<Listing> findPurchasedListingsByUser(@PathVariable String username){
        return service.findPurchasedListingsByUser(username);
    }



    @GetMapping("/{makeId}")
    public List<Listing> findByMakeId(@PathVariable int makeId){
        return service.findByMakeId(makeId);
    }

    @GetMapping("/{modelId}")
    public List<Listing> findByModelId(@PathVariable int modelId){
        return service.findByModelId(modelId);
    }


    @GetMapping("/{}")
    public List<Listing> findByPriceRange(@PathVariable Integer min, @PathVariable Integer max){
        return service.findByPriceRange(min, max);
    }


    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Listing listing){
        Result<Listing> result = service.add(listing);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<Object> edit(@PathVariable int listingId, @RequestBody Listing listing){
        if(listingId != listing.getListingId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Listing> result = service.edit(listing);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable int listingId){
        if(service.deleteById(listingId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
