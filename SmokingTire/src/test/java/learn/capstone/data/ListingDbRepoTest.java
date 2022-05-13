package learn.capstone.data;

import learn.capstone.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
//class ListingDbRepoTest {
//
//
//    final static int NEXT_ID = 9;
//
//    @Autowired
//    ListingDbRepo repo;
//
//    @Autowired
//    KnownGoodState knownGoodState;
//
//    @BeforeEach
//    void setup() {
//        knownGoodState.set();
//    }
//
//
//    @Test
//    void shouldFindAllAvailableListings() {
//        List<Listing> actual = repo.findAllAvailableListings();
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void shouldFindAllPurchasedListings() {
//        List<Listing> actual = repo.findPurchasedListingsByUser("bob");
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void shouldFindByMakeId() {
//        List<Listing> actual = repo.findByMakeId(1);
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void shouldFindByModelId() {
//        List<Listing> actual = repo.findByModelId(1);
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void shouldFindByPriceRange() {
//        List<Listing> actual = repo.findByPriceRange(50000, 80000);
//        assertEquals(1, actual.size());
//
//        actual = repo.findByPriceRange(100000, 130000);
//        assertEquals(1, actual.size());
//    }
//
//    @Test
//    void shouldFindById() {
//        Listing actual = repo.findByListingId(2);
//        assertEquals("this is a public listing", actual.getDescription());
//        assertEquals("bob", actual.getListingUser().getUsername());
//        assertEquals("Viper", actual.getCar().getMake().getModel().getModelName());
//    }
//
//    @Test
//    void shouldAdd() {
//        Listing listing = makeListing();
//        Listing actual = repo.add(listing);
//        assertNotNull(actual);
//        assertEquals(3, actual.getListingId());
//    }
//
//
//    @Test
//    void shouldEdit() {
//        Listing listing = repo.findByListingId(3);
//        assertEquals("The Ultimate Driving Machine.", listing.getDescription());
//
//        listing.setDescription("The not so ultimate driving machine.");
//        assertTrue(repo.edit(listing));
//        assertEquals("The not so ultimate driving machine.", listing.getDescription());
//    }
//
//    @Test
//    void shouldDeleteById() {
//
//        assertTrue(repo.deleteById(1));
//        assertFalse(repo.deleteById(1));
//
//    }
//
//    @Test
//    void shouldIncreaseViewCount() {
//        Listing actual = repo.findByListingId(1);
//        assertEquals(6523, actual.getViewCount());
//
//        assertTrue(repo.increaseViewCount(actual));
//        actual = repo.findByListingId(1);
//        assertEquals(6524, actual.getViewCount());
//    }
//
//    @Test
//    void shouldConvertToSold() {
//        Listing actual = repo.findByListingId(2);
//        assertEquals(1, repo.findAllAvailableListings().size());
//
//        AppUser purchaser = new AppUser(2, "june"
//                , "$2a$12$k2TB.cQ1TLHLOYn.pbbiTuQ5HoUxozWkl.ZgFZ.9eioAeMxndT5AS"
//                , Collections.singleton("ADMIN"));
//
//        assertTrue(repo.convertToSold(actual.getListingId(), purchaser));
//
//        assertEquals(0, repo.findAllAvailableListings().size());
//    }
//
//
//    private Listing makeListing() {
//        Listing listing = new Listing();
//        listing.setDescription("The Ultimate Driving Machine.");
//        listing.setViewCount(2200);
//        listing.setPrice(67000);
//        listing.setMileage(100000);
//
//        Model model = new Model(2, "Viper", 2004);
//        Make make = new Make(2, "Dodge", model);
//        Car car = new Car(1, 500, "rear-wheel drive", "roadster", "manual", make);
//        listing.setCar(car);
//
//        listing.setListingUser(new AppUser(1, "bob"
//                , "$2a$12$HqaU3VlN09ufZ60R8VrLHuIX8H6b1iFDA9AG./vzThpIzhxEIF8nC"
//                , Collections.singleton("USER")));
//        return listing;
//    }
//}