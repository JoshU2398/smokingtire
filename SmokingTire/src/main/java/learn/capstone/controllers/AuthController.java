package learn.capstone.controllers;

import learn.capstone.domain.Result;
import learn.capstone.domain.UserService;
import learn.capstone.models.AppUser;
import learn.capstone.models.Listing;
import learn.capstone.security.JwtConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/security")
public class AuthController {
    AuthenticationManager authenticationManager;
    JwtConverter jwtConverter;
    UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter jwtConverter, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtConverter = jwtConverter;
        this.userService = userService;
    }

    @GetMapping("/findUser/{username}")
    public UserDetails findByUsername(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> credentials) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                credentials.get("username"), credentials.get("password"));

        try {
            Authentication authResult = authenticationManager.authenticate(token);
            if (authResult.isAuthenticated()){
                String jwt = jwtConverter.getTokenFromUser((User)authResult.getPrincipal() );
                Map<String, String> tokenWrapper = new HashMap<>();
                tokenWrapper.put("jwt_token", jwt);
                return ResponseEntity.ok(tokenWrapper);
            }
        }catch (AuthenticationException ex){
            ex.printStackTrace(System.err);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register/{username}/{password}")
    public ResponseEntity register(@PathVariable String username, @PathVariable String password) {
        Result<AppUser> result = userService.create(username, password);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId) {
        if (userService.remove(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId, @RequestBody AppUser toUpdate) {
        if (userId != toUpdate.getUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<AppUser> result = userService.edit(toUpdate);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

}
