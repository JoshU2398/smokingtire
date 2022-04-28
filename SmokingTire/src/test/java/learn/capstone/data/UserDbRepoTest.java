package learn.capstone.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDbRepoTest {

    @Autowired
    UserDbRepo repo;

    @Autowired
    KnownGoodState knownGoodState;

    @Test
    void findByUsername() {
    }

    @Test
    void findById() {
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void edit() {
    }
}