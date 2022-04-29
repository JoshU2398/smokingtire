package learn.capstone.domain;

import learn.capstone.data.ListingRepo;
import learn.capstone.models.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {


    private final ListingRepo repo;

    public ListingService(ListingRepo repo){
        this.repo = repo;
    }

    public List<Listing> findByMakeId(Integer makeId){
        throw new UnsupportedOperationException();
    }

    public List<Listing> findByModelId(Integer modelId){
        throw new UnsupportedOperationException();
    }

    public Result<Listing> add(Listing Listing){
        throw new UnsupportedOperationException();
    }

    public Result<Listing> edit(Listing listing){
        throw new UnsupportedOperationException();
    }

    public boolean deleteById(Integer listingId){
        throw new UnsupportedOperationException();
    }

}
