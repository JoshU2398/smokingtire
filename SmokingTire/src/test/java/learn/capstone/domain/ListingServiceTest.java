package learn.capstone.domain;

import learn.capstone.data.ListingRepo;
import learn.capstone.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ListingServiceTest {

    @Autowired
    ListingService service;

    @MockBean
    ListingRepo repo;

    @Test
    void shouldFindOneListing() {
        List<Listing>listings = new ArrayList<>();
        Listing expected = makeAvailableListing();
        listings.add(expected);
        when(repo.findAllAvailableListings()).thenReturn(listings);

        List<Listing> actual = service.findAllAvailableListings();

        assertEquals(listings, actual);
    }

    @Test
    void findPurchasedListingsByUser() {
        List<Listing>listings = new ArrayList<>();
        Listing expected = makePurchasedListing();
        listings.add(expected);
        when(repo.findPurchasedListingsByUser("Test Username")).thenReturn(listings);

        List<Listing> actual = service.findPurchasedListingsByUser("Test Username");

        assertEquals(listings, actual);
    }

    @Test
    void findByMakeId() {
        List<Listing>listings = new ArrayList<>();
        Listing expected = makeAvailableListing();
        listings.add(expected);
        when(repo.findByMakeId(1)).thenReturn(listings);

        List<Listing> actual = service.findByMakeId(1);

        assertEquals(listings, actual);
    }

    @Test
    void findByModelId() {
        List<Listing>listings = new ArrayList<>();
        Listing expected = makeAvailableListing();
        listings.add(expected);
        when(repo.findByModelId(1)).thenReturn(listings);

        List<Listing> actual = service.findByModelId(1);

        assertEquals(listings, actual);
    }

    @Test
    void findByPriceRange() {
        List<Listing>listings = new ArrayList<>();
        Listing expected = makeAvailableListing();
        listings.add(expected);
        when(repo.findByPriceRange(0, 10000)).thenReturn(listings);

        List<Listing> actual = service.findByPriceRange(0, 10000);

        assertEquals(listings, actual);
    }

    @Test
    void shouldAddWhenValid() {
        Listing listing = makeAvailableListing();
        Result<Listing> result = service.add(listing);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldEditWhenValid() {
        Listing listing = makeAvailableListing();


        Result<Listing> result = service.edit(listing);

    }

    @Test
    void shouldDeleteById() {
        Listing listing = makeAvailableListing();
        Result<Listing> result = service.add(listing);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(1, listing.getListingId());

        assertTrue(service.deleteById(1));
    }

    Listing makeAvailableListing() {
        //listingId, price, mileage, postDate, description, viewCount, user, car, isAvailable
        Listing listing = new Listing();
        listing.setListingId(1);
        listing.setPrice(20000);
        listing.setMileage(110000);
        listing.setPostDate(LocalDate.now());
        listing.setDescription("This is my dummy available listing.");
        listing.setViewCount(3);
        listing.setUser(makeUser());
        listing.setCar(makeCar());
        listing.setAvailable(true);

        return listing;
    }

    Listing makePurchasedListing() {
        //listingId, price, mileage, postDate, description, viewCount, user, car, isAvailable
        Listing listing = new Listing();
        listing.setListingId(2);
        listing.setPrice(20000);
        listing.setMileage(110000);
        listing.setPostDate(LocalDate.now());
        listing.setDescription("This is my dummy purchased listing.");
        listing.setViewCount(3);
        listing.setUser(makeUser());
        listing.setCar(makeCar());
        listing.setAvailable(false);

        return listing;
    }

    AppUser makeUser(){
        //Integer userId, String username, String password, Set<String> roles
        return new AppUser(1, "TestUser", "TestPassword", Collections.singleton("USER"));
    }

    Car makeCar(){
        // Integer carId, Integer horsepower, String drivetrain, String chassis, String transmission, Make make
        Car car = new Car();
        car.setCarId(1);
        car.setHorsepower(400);
        car.setDrivetrain("Test Drivetrain");
        car.setChassis("Test Chassis");
        car.setTransmission("Test Transmission");
        car.setMake(makeMake());

        return car;
    }

    Make makeMake(){
        // Integer makeId, String makeName, Model model
        Make make = new Make();
        make.setMakeId(1);
        make.setMakeName("Test Make Name");
        make.setModel(makeModel());

        return make;
    }

    Model makeModel(){
        //Integer modelId, String modelName, Integer modelYear
        Model model = new Model();
        model.setModelId(1);
        model.setModelName("Test Model Name");
        model.setModelYear(2000);

        return model;
    }
}