package learn.capstone.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ListingDbRepoTest {

    @Autowired
    ListingDbRepo repo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void findAllListings() {
    }

    @Test
    void findByMakeId() {
    }

    @Test
    void findByModelId() {
    }

    @Test
    void findByPriceRange() {
    }

    @Test
    void add() {
    }

    @Test
    void edit() {
    }

    @Test
    void deleteById() {

    assertTrue(repo.deleteById(1));
    assertFalse(repo.deleteById(1));

    }
}