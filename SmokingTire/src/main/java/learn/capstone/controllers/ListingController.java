package learn.capstone.controllers;


import learn.capstone.domain.ListingService;
import learn.capstone.domain.Result;
import learn.capstone.models.AppUser;
import learn.capstone.models.Listing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService service;

    public ListingController(ListingService service) {
        this.service = service;
    }

    @GetMapping("/findAvailable")
    public List<Listing> findAllAvailableListings(){
        return service.findAllAvailableListings();
    }


    @GetMapping("/userPurchased/{username}")
    public List<Listing> findPurchasedListingsByUser(@PathVariable String username) {
        return service.findPurchasedListingsByUser(username);
    }

    @GetMapping("/userSelling/{username}")
    public List<Listing> findAllAvailableByUser(@PathVariable String username) {
        return service.findAllAvailableByUser(username);
    }

    @GetMapping("/findByMake/{makeId}")
    public List<Listing> findByMakeId(@PathVariable int makeId){
        return service.findByMakeId(makeId);
    }

    @GetMapping("/findByModel/{modelId}")
    public List<Listing> findByModelId(@PathVariable int modelId){
        return service.findByModelId(modelId);
    }


    @GetMapping("/findByPriceRange/{min}/{max}")
    public List<Listing> findByPriceRange(@PathVariable Integer min, @PathVariable Integer max){
        return service.findByPriceRange(min, max);
    }

    @GetMapping("/findListing/{listingId}")
    public Listing findByListingId(@PathVariable Integer listingId) {
        return service.findByListingId(listingId);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody Listing listing){
        Result<Listing> result = service.add(listing);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/edit/{listingId}")
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

    @DeleteMapping("/delete/{listingId}")
    public ResponseEntity<Void> deleteById(@PathVariable int listingId){
        if(service.deleteById(listingId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/increaseViewCount/{listingId}")
    public ResponseEntity<Object> increaseViewCount(@PathVariable Integer listingId, @RequestBody Listing listing) {
        if (!Objects.equals(listingId, listing.getListingId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Listing> result = service.increaseViewCount(listing);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/convertToSold/{listingId}")
    public ResponseEntity<Object> convertToSold(@PathVariable Integer listingId, @RequestBody AppUser purchaser) {
        Result<Listing> result = service.convertToSold(listingId, purchaser);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

}
