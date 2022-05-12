package learn.capstone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtConverter converter;

    @Override
    protected void configure( HttpSecurity http ) throws Exception{
        http.csrf().disable();
        http.cors();

        http.authorizeRequests()
                //TODO: add antMatchers
                .antMatchers( HttpMethod.POST, "/api/security/login").permitAll()
                .antMatchers( HttpMethod.POST, "/api/security/register/*/*").permitAll()
                .antMatchers( HttpMethod.DELETE, "/api/security/delete/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.PUT, "/api/security/update/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.GET, "/api/listings/findAvailable").permitAll()
                .antMatchers( HttpMethod.GET, "/api/listings/findByMake/*").permitAll()
                .antMatchers( HttpMethod.GET, "/api/listings/findByModel/*").permitAll()
                .antMatchers( HttpMethod.GET, "/api/listings/findListing/*").permitAll()
                .antMatchers( HttpMethod.GET, "/api/listings/increaseViewCount/*").permitAll()
                .antMatchers( HttpMethod.GET, "/api/listings/userPuchased/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.GET, "/api/listings/userSelling/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.POST, "/api/listings/add").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.PUT, "/api/listings/edit/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.DELETE, "/api/listings/delete/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.DELETE, "/api/listings/convertToSold/*").hasAnyRole("ADMIN", "USER")
                .antMatchers( HttpMethod.GET, "/api/cars/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/**").permitAll()
                .and()
                .addFilter( new JwtRequestFilter(converter, authenticationManager() ))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

}
