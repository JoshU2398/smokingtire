package learn.capstone.controllers;

import learn.capstone.domain.UserService;
import learn.capstone.security.JwtConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
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
}
