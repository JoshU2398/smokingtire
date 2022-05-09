package learn.capstone.data;

import learn.capstone.models.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageDbRepoTest {

    @Autowired
    KnownGoodState knownGoodState;

    @Autowired
    ImageDbRepo repo;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        List<Image> actual = repo.findAll();
        assertEquals(2, actual.size());
    }

    @Test
    void findById() {
        Image actual = repo.findById(1);
        assertEquals("test_url", actual.getImageUrl());
    }

    @Test
    void findImagesByListing() {
        List<Image> actual = repo.findImagesByListing(1);
        assertNotNull(actual);
    }

    @Test
    void add() {
        Image toAdd = new Image("dummyUrl", 1);
        Image actual = repo.add(toAdd);
        assertNotNull(actual);
    }

    @Test
    void update() {
        Image toUpdate = repo.findById(1);
        toUpdate.setImageUrl("Updated url");
        assertTrue(repo.update(toUpdate));
    }

    @Test
    void deleteById() {
        assertTrue(repo.deleteById(1));
    }
}