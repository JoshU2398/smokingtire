package learn.capstone.data;


import learn.capstone.models.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ModelDbRepoTest {

    @Autowired
    ModelDbRepo repo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAll() {
        List<Model> models = repo.findAll();
        assertNotNull(models);
        assertTrue(models.size() >= 2);
    }
}