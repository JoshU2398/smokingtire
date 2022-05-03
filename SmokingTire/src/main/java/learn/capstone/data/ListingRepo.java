package learn.capstone.data;

import learn.capstone.models.AppUser;
import learn.capstone.models.Listing;

import java.util.List;

public interface ListingRepo {

    List<Listing> findAllAvailableListings();

    List<Listing> findPurchasedListingsByUser(String username);

    List<Listing> findAllAvailableByUser(String username);

    List<Listing> findByMakeId(int makeId);

    List<Listing> findByModelId(int modelId);

    List<Listing> findByPriceRange(Integer min, Integer max);

    Listing findByListingId(int listingId);

    Listing add(Listing toAdd);

    boolean edit(Listing toEdit);

    boolean deleteById(int listingId);

    boolean increaseViewCount(Listing toUpdate);

    boolean convertToSold(int listingId, AppUser purchaser);

}
