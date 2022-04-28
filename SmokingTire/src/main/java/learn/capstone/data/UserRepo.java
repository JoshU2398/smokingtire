package learn.capstone.data;

import learn.capstone.models.AppUser;

public interface UserRepo {

    AppUser findByUsername(String username );

    AppUser add( AppUser toAdd );

    boolean remove( Integer userId );

    void edit( AppUser updated );

    AppUser findById(Integer userId);

}