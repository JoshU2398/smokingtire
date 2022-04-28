package learn.capstone.data;

import learn.capstone.models.Listing;

import java.util.List;

public interface ListingRepo {

    List<Listing> findAllListings();

    List<Listing> findByMakeId(int makeId);

    List<Listing> findByModelId(int modelId);

    List<Listing> findByPriceRange(Integer min, Integer max);

    Listing add(Listing toAdd);

    boolean edit(Listing toEdit);

    boolean deleteById(int listingId);
}
