package learn.capstone.domain;

import learn.capstone.data.UserRepo;
import learn.capstone.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    UserRepo repo;
    PasswordEncoder encoder;

    public UserService( UserRepo repo, PasswordEncoder encoder ){
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

    public AppUser create( String username, String password ){
        //TODO: use encoder to hash password
        //          then actually implement...

        return null;
    }

}
