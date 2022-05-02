package learn.capstone.data;

import learn.capstone.models.Listing;

import java.util.List;

public interface ListingRepo {

    List<Listing> findAllAvailableListings();

    List<Listing> findPurchasedListingsByUser(String username);

    List<Listing> findByMakeId(int makeId);

    List<Listing> findByModelId(int modelId);

    List<Listing> findByPriceRange(Integer min, Integer max);

    Listing findById(int listingId);

    Listing add(Listing toAdd);

    boolean edit(Listing toEdit);

    boolean deleteById(int listingId);

}
