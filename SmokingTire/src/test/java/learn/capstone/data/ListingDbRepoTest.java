package learn.capstone.data;

import learn.capstone.models.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

class ListingDbRepoTest {


    final static int NEXT_ID = 9;

    @Autowired
    ListingDbRepo repo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAllListings() {
    }

    @Test
    void shouldFindByMakeId() {
    }

    @Test
    void shouldFindByModelId() {
    }

    @Test
    void shouldFindByPriceRange() {
    }

    @Test
    void shouldAdd() {
        Listing listing = makeListing();
        Listing actual = repo.add(listing);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getListingId());

        listing = makeListing();
        listing.setPostDate(null);
        actual = repo.add(listing);
        assertNotNull(actual);
        assertEquals(NEXT_ID + 1, actual.getListingId());

    }


    @Test
    void shouldEdit() {
        Listing listing = makeListing();
        listing.setListingId(2);
        assertTrue(repo.edit(listing));
        listing.setListingId(13);
        assertFalse(repo.edit(listing));
    }

    @Test
    void shouldDeleteById() {

    assertTrue(repo.deleteById(1));
    assertFalse(repo.deleteById(1));

    }


    private Listing makeListing() {
        Listing listing = new Listing();
        listing.setDescription("The Ultimate Driving Machine.");
        listing.setPostDate(LocalDate.of(2021, 3, 4));
        listing.setViewCount(2200);
        listing.setPrice(67000);
        listing.setCar("BMW M3");
        listing.setUser("Marcus");
        return listing;
    }
}