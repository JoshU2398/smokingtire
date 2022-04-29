package learn.capstone.data;

import learn.capstone.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;

class UserDbRepoTest {

    private static final int NEXT_ID = 9;

    @Autowired
    UserDbRepo repo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByUsername() {
    }

    @Test
    void shouldFindById() {
    }

    @Test
    void shouldAdd() {
        AppUser user = makeUser();
        AppUser actual = repo.add(user);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getUserId());


    }

    @Test
    void shouldRemove() {
        assertTrue(repo.remove(2));
        assertFalse(repo.remove(2));
    }

    @Test
    void shouldEdit() {
        AppUser user = makeUser();
        user.setUserId(2);
        assertTrue(repo.edit(user));
        user.setUserId(13);
        assertFalse(repo.edit(user));
    }


    private AppUser makeUser(){
        AppUser user = new AppUser(3, "speed_demon69", "donutcity", "USER");
        return user;
    }
}