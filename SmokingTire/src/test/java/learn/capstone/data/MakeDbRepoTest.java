package learn.capstone.data;

import learn.capstone.models.Make;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MakeDbRepoTest {

    @Autowired
    MakeDbRepo repo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAll() {
        List<Make> makes = repo.findAll();
        assertNotNull(makes);
        assertTrue(makes.size() >= 2);
    }
}