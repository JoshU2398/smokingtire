package learn.capstone.domain;

import learn.capstone.data.ListingRepo;
import learn.capstone.models.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class ListingService {


    private final ListingRepo repo;

    public ListingService(ListingRepo repo){
        this.repo = repo;
    }

    public List<Listing> findAllAvailableListings() {
        return repo.findAllAvailableListings();
    }

    public List<Listing> findPurchasedListingsByUser(String username) {
        return repo.findPurchasedListingsByUser(username);
    }

    public List<Listing> findByMakeId(Integer makeId){
        return repo.findByMakeId(makeId);
    }

    public List<Listing> findByModelId(Integer modelId){
        return repo.findByModelId(modelId);
    }

    public List<Listing> findByPriceRange(Integer min, Integer max) {
        return repo.findByPriceRange(min, max);
    }

    public Result<Listing> add(Listing listing){
        Result<Listing> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

        if(!violations.isEmpty()){
            for (ConstraintViolation<Listing> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        result.setPayload(repo.add(listing));
        return result;
    }

    public Result<Listing> edit(Listing listing){
        Result<Listing> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

        if(!violations.isEmpty()){
            for (ConstraintViolation<Listing> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (!repo.edit(listing)){
            String msg = String.format("ListingId: %s, not found", listing.getListingId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(Integer listingId){
        return repo.deleteById(listingId);
    }

}
