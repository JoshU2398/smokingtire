package learn.capstone.domain;

import learn.capstone.data.UserRepo;
import learn.capstone.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    UserRepo repo;
    PasswordEncoder encoder;

    public UserService(UserRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null) {
            return null;
        }
        AppUser matchingUser = repo.findByUsername(username);

        if( matchingUser == null ){
            throw new UsernameNotFoundException(username + " not found.");
        }
        return matchingUser;
    }

    public Result<AppUser> create(String username, String password){
        Result<AppUser> result = new Result<>();
        if (username == null || password == null) {
            result.addMessage("Username and password can not be null.", ResultType.INVALID);
            return result;
        }

        if (repo.findByUsername(username) != null) {
            result.addMessage("This username already exists.", ResultType.INVALID);
            return result;
        }

        String encodedPassword = encoder.encode(password);
        AppUser toCreate = new AppUser(null, username, encodedPassword, Collections.singleton("USER"));

        toCreate = repo.add(toCreate);
        result.setPayload(toCreate);

        return result;
    }

    public boolean remove(Integer userId) {
        return repo.remove(userId);
    }

    public Result<AppUser> edit(AppUser toEdit) {
        Result<AppUser> result = new Result<>();

        if (toEdit.getUsername() == null || toEdit.getPassword() == null) {
            result.addMessage("Username and password can not be null.", ResultType.INVALID);
            return result;
        }

        if (repo.findByUsername(toEdit.getUsername()) != null
                && repo.findByUsername(toEdit.getUsername()).getUserId() != toEdit.getUserId()) {
            result.addMessage("This username already exists.", ResultType.INVALID);
            return result;
        }

        String encodedPassword = encoder.encode(toEdit.getPassword());
        AppUser updated = new AppUser(toEdit.getUserId(), toEdit.getUsername(), encodedPassword, Collections.singleton("USER"));

        if (!repo.edit(updated)) {
            String msg = String.format("userId: %s, not found", updated.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

}
