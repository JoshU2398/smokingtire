package learn.capstone.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CarDbRepoTest {

    @Autowired
    CarDbRepo repo;

    @Test
    void shouldFindAll() {

    }
}