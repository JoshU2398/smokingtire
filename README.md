# PLAN

# Smoking Tires

## Smoking Tires Data Model

    1. users
    2. roles
    3. cars
    4. makes
    5. models
    6. listings

## Roles

    1. Guest/Anonymous Role
    2. Standard User Role
    3. Administrator Role

## User Stories

* [ ] As an admin I should be able to delete any listing.
* [ ] As an admin I should be able to delete any comment.
* [ ] As an admin I should be able to edit any listing.
* [ ] As an admin I should be able to add a listing.
* [ ] As an admin I should be able to view all purchase histories.
* [ ] As a user I should be able to add a listing.
* [ ] As a user I should be able to delete my own listing.
* [ ] As a user I should be able to edit my own listing.
* [ ] As a user I should be able to edit my own listing.
* [ ] As a user I should be able to view other listings.
* [ ] As a user I should be able to buy other listings.
* [ ] As a user I should be able to comment under any listing.
* [ ] As a user I should be able to delete my own comments.
* [ ] As a user I should be able to view my own purchase history.
* [ ] As a user I should not be able to edit another user’s listings.
* [ ] As a user I should not be able to delete another user’s listings.
* [ ] As a guest I should be able view listings.
* [ ] As a guest I should be able to login.
* [ ] As a guest I should not be able to add any listings.
* [ ] As a guest I should not be able to edit any listings.
* [ ] As a guest I should not be able to delete any listings.
* [ ] As a guest I should not be able to comment under any listings.



## Tasks
* [ ] Create Java API
  * [x] Create Java Project (SmokingTire)
  * [ ] Modify pom.xml to include the parent tag (spring-boot-starter-parent)
  * [ ] Modify pom.xml to include the following dependencies
    * [ ] spring-boot-starter-security
    * [ ] jjwt-api
    * [ ] jjwt-impl
    * [ ] jjwt-jackson
    * [ ] mysql-connector-java
    * [ ] spring-boot-starter-jdbc
    * [ ] spring-boot-starter-web
  * [ ] Create base package (capstone)
    * [ ] Create App class
      * [ ] @SpringBootApplication
      * [ ] main
        * [ ] SpringApplication.run( App.class, args );
  * [ ] Create application.properties file
    * [ ] spring.datasource.url=jdbc:mysql://localhost:3306/`<name of database>`
    * [ ] spring.datasource.username=root
    * [ ] spring.datasource.password=top-secret-password
  * [ ] Create models package
    * [ ] Create AppUser class
      * [ ] Extend from the User (org.springframework.security.core.userdetails)
      * [ ] Add Set&lt;String&gt; roles field variable
      * [ ] Add Integer userId field variable
      * [ ] Generate getters/setters
      * [ ] Generate hashCode/equals
      * [ ] Add constructor which takes Integer userId, String username, String password, and Set&lt;String&gt; roles
        * [ ] call super(username, password, roles.stream().map( r -> new SimpleGrantedAuthority( "ROLE_" + r )).collect( Collectors.toList() ) )
        * [ ] assign to this.userId
        * [ ] assign to this.roles
    * [ ] Create Listing Class
        * [ ] Fill out Listing Class
            * [ ] create a `int viewCount` attribute.
    * [ ] Create Car Class
        * [ ] Fill out CarClass        
    * [ ] Create Make Class
        * [ ] Fill out Make Class    
    * [ ] Create Model Class
        * [ ] Fill out Model Class
  * [ ] Create data package
    * [ ] Create ListingRepo Interface
        * [ ] Create `findAll()` method
        * [ ] Create `findByMakeId(int makeId)` method
        * [ ] Create `findByModelId(int modelId)` method
        * [ ] Create `findByPriceRange(BigDecimal min, BigDecimal max)` method
        * [ ] Create `add()` method
        * [ ] Create `edit()` method
        * [ ] Create `delete()` method
    * [ ] Create `ListingDbRepo` Class
        * [ ] Create auto-generate methods from interface.
        * [ ] Fill out methods in class (fill out plan before coding).
    * [ ] Create `ListingMapper` Class
        * [ ] implement RowMapper&lt;Listing&gt; class.
        * [ ] Fill out the auto-generated methods (plan first!).
    * [ ] Create UserMapper Class
      * [ ] create Set&lt;String&gt; roles field variable
      * [ ] create UserMapper constructor which takes in the Set of roles and sets the field variable
      * [ ] implements RowMapper&lt;AppUser&gt;
      * [ ] auto-generate methods
        * [ ] AppUser toBuild = new AppUser(userId, username, password, roles);
    * [ ] Create UserDbRepo class
      * [ ] Add @Repository
      * [ ] implements UserRepo
        * [ ] Add @Autowired JdbcTemplate template field variable
        * [ ] generate functions automatically
        * [ ] create private Set&lt;String&gt; findRolesByUsername(String username)
          * [ ] String sql = "SELECT roleName FROM users u inner join userroles ur on ur.userId = u.userId inner join roles r on ur.roleId = r.roleId where username = ?"
          * [ ] return template.query( sql, (rowData, rowNum)->rowData.getString("roleName"), username).stream().collect(Collectors.toSet())
        * [ ] implement findByUsername(String username)
          * [ ] String sql = "select userId, username, password from users where username = ?"
          * [ ] return template.query( sql, new UserMapper(findRolesByUsername(username)), username).stream().findAny().orElse(null);
  * [ ] Create domain package
    * [ ] Create InvalidUserException
      * [ ] create constructor that takes in String message, call super(message)
      * [ ] create constructor that takes in String message, Throwable innerException calls super( message, innerException )
    * [ ] Create UserService class
      * [ ] mark with @Service
      * [ ] implements UserDetailsService
      * [ ] add UserRepo field variable
      * [ ] add PasswordEncoder field variable
      * [ ] add constructor which takes in a UserRepo & PasswordEncoder
      * [ ] @Override loadUserByUsername (can return AppUser as a UserDetails object)
        * [ ] use the repo to pass along the user
        * [ ] add //listing: validate (later we'll check to make sure username isn't null/empty/etc)
        * [ ] if user is not found (we get a null) throw new UsernameNotFoundException(username + " not found")
        * [ ] otherwise, return the user
      * [ ] add AppUser create( String username, String password )
        * [ ] for now just return null
    * [ ] Create ListingService class
      * [ ] mark as @Service
      * [ ] add @Autowired ListingRepo lRepo field variable
      * [ ] add @Autowired UserRepo uRepo field variable
      * [ ] -- should have autogenerated getListings method from controller --
        * [ ] return lRepo.findAll();
      * [ ] create public void deleteById(Integer listingId, Principal user) throws InvalidUserException {
        * [ ] Listing toDelete = lRepo.findById(listingId);
        * [ ] AppUser requester = uRepo.findByUsername(user.getName());
        * [ ] if( requester.getRoles().contains("ADMIN") || requester.getUserId().intValue() == toDelete.getUserId().intValue() ){
          * [ ] lRepo.remove(listingId);
        * [ ] } else { throw new InvalidUserException("Only admins or the owner of the listing may delete it."); }
  * [ ] Create security package
    * [ ] create SecurityConfig class <b>EDIT TO FIT OUR NEEDS (PLAN FIRST!)</b>
      * [ ] @EnableWebSecurity
      * [ ] extends WebSecurityConfigurerAdapter
      * [ ] @Override protected void configure( HttpSecurity http) throws Exception
        * [ ] http.csrf().disable()
        * [ ] http.cors()
        * [ ] http.authorizeRequests()
          * [ ] .antMatchers( HttpMethod.POST, "/api/security/login").permitAll()
          * [ ] .antMatchers( HttpMethod.GET, "/api/listing/public" ).permitAll()
          * [ ] .antMatchers( HttpMethod.DELETE, "/api/listing/*").hasAnyRole("AUTHOR", "ADMIN")
          * [ ] .antMatchers("/**").denyAll()
          * [ ] .and()
          * [ ] .sessionManagement()
            * [ ] .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      * [ ] public PasswordEncoder getEncoder(){ return new BCryptPasswordEncoder(); }
        * [ ] mark with @Bean
      * [ ] @Override protected AuthenticationManager authenticationManager() throws Exception
        * [ ] just return super.authenticationManager();
        * [ ] mark with @Bean
    * [ ] Create JwtConverter class
      * [ ] Mark as @Component
      * [ ] add a Key field variable (secretKey) assign Keys.secretKeyFor(SignatureAlgorithm.HS256)
      * [ ] add public String getTokenFromUser( User toConvert )
        * [ ] generate comma separated string of authorities granted to the user (retrieve those with .getAuthorities() )
        * [ ] return Jwts.builder()
          * [ ] .setIssuer("SmokingTire")
          * [ ] .setSubject(toConvert.getUsername())
          * [ ] .claim("authorties", commaSeparatedString)
          * [ ] .setExpiration( new Date(System.currentTimeMillis() + 15 * 60 * 1000 ) )
          * [ ] .signWithKey( secretKey )
          * [ ] .compact();
      * [ ] add public User getUserFromToken( String token )
        * [ ] try/catch (JwtException)
          * [ ] JwtParser parser = Jwts.parserBuilder().requireIssuer("listing-app").setSigningKey( secretKey ).build();
          * [ ] Jws&lt;Claims&gt; claims = parser.parseClaimsJws( token.substring(7) );
          * [ ] String username = claims.getBody().getSubject();
          * [ ] String authorities = (String)claims.getBody().get("authorities");
          * [ ] String [] authSplit = authorities.split(",");
          * [ ] List&lt;GrantedAuthority&gt; grantedAuthorities = new ArrayList<>();
          * [ ] for( String auth : authSplit ){ grantedAuthorities.add(new SimpleGrantedAuthority(auth)); }
          * [ ] return new User( username, username, grantedAuthorities );
          * [ ] catch( JwtException ex ) {
            * [ ] ex.printStackTrace( System.err );
            * [ ] return null; }
    * [ ] Create JwtRequestFilter class
      * [ ] extends BasicAuthenticationFilter
      * [ ] Add a JwtConverter field
      * [ ] Add a constructor that takes in a JwtConvert and AuthenticationManager
        * [ ] super( authManager )
        * [ ] store the JwtConverter in the field variable
      * [ ] @Override protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        * [ ] String authHeader = request.getHeader( "Authorization");
        * [ ] if( authHeader != null && authHeader.startsWith( "Bearer ")){
          * [ ] User converted = converter.getUserFromToken( authHeader );
          * [ ] if( converted != null ){
            * [ ] UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( converted.getUsername(), null, convertedUser.getAuthorities() );
            * [ ] SecurityContextHolder.getContext().setAuthentication( token );
          * [ ] } else {
            * [ ] response.setStatus( 403 ); }
        * [ ] chain.doFilter( request, response );
      * [ ] IN SecurityConfig.java
        * [ ] add @Autowired JwtConverter field variable
        * [ ] right after the .and() call .addFilter( new JwtReqestFilter() )
  * [ ] Create controllers package
    * [ ] Add AuthController class
      * [ ] mark as @RestController
      * [ ] add @RequestMapping( "/api/security" )
      * [ ] add AuthenticationManager field variable
      * [ ] add JwtConverter field variable
      * [ ] add UserService field variable
      * [ ] add a constructor that takes in all field variables and sets them
      * [ ] add ResponseEntity login( @RequestBody Map&lt;String,String&gt; credentials )
        * [ ] mark as @PostMapping("/login")
        * [ ] create UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( credentials.get("username"), credentials.get("password") );
        * [ ] in a try/catch( AuthenticationException ex) block...
          * [ ] Authentication authResult = authManager.authenticate( token );
          * [ ] if( authResult.isAuthenticated() ){
            * [ ] String jwt = converter.getTokenFromUser( (User)authResult.getPrincipal());
            * [ ] Map&lt;String,String&gt; tokenWrapper = new HashMap<>();
            * [ ] tokenWrapper.put( "jwt_token", jwt);
            * [ ] return ResponseEntity.ok( tokenWrapper );
          * [ ] }
          * [ ] catch( AuthenticationException ex ){
            * [ ] ex.printStackTrace( System.err ); }
          * [ ] return new ResponseEntity( HttpStatus.FORBIDDEN );
    * [ ] Add ListingController class
      * [ ] mark as @RestController
      * [ ] @RequestMapping( "/api/listing" )
      * [ ] add @Autowired ListingService field variable (service)
      * [ ] add a GET endpoint for retrieving all Listings
        * [ ] List&lt;Listing&gt; allListings = service.getAllListings()
        * [ ] generate ListingService.getAllListings()
        * [ ] return ResponseEntity.ok(allListings);
      * [ ] add a GET endpoint ("/{listingId}) for retrieving one Listing
        * [ ] if legal, add a helper function that increases views by one.
      * [ ] add a GET endpoint ("/{makeId}") for retrieving listings filtered by Make.
      * [ ] add a GET endpoint ("/{modelId}") for retrieving listings filtered by Model
      * [ ] add a GET endpoint ("/{min}/{max}") for retrieving Listings filtered by price range.
      * [ ] add a POST ("/add") endpoint for creating new listings.
      * [ ] add a PUT endpoint ("/edit/"{/listingId}") for editing an existing listing.
      * [ ] add a DELETE endpoint ("/{listingId}")
        * [ ] public ResponseEntity delete( @PathVariable Integer listingId, Principal user ){
          * [ ] service.deleteById( listingId, user );
          * [ ] generate listingService.deleteById()
          * [ ] return ResponseEntity.ok().build();
* [ ] Create mysql schemas (test/prod)
  * [ ] create sql folder in project folder
  * [ ] create smokingtires-test.sql
  * [ ] create smokingtires-prod.sql
  * [ ] drop database if exists smokingtires_X
  * [ ] create database smokingtires_X
  * [ ] use smokingtires_X
  * [ ] create table users
    * [ ] userId        int primary key auto_increment
    * [ ] username      varchar(300) not null unique
    * [ ] password      varchar(2048) not null,
  * [ ] create table listings
    * [ ] listingId        int primary key auto_increment,
    * [ ] listingText      text not null,
    * [ ] userId           int not null,
    * [ ] carId            int primary key auto_increment,
    * [ ] createDate       date not null,
    * [ ] views            int not null,
    * [ ] constraint fk_listings_users foreign key (userId) references users(userId)
    * [ ] constraint fk_listings_cars foreign key (carId) references cars(carId)
  * [ ] create table cars
    * [ ] carId         int primary key auto_increment
    * [ ] price         int not null,
    * [ ] horsepower    int not null,
    * [ ] drivetrain    varchar(20) not null,
    * [ ] chassis       varchar(50) not null,
    * [ ] drivetrain    varchar(20) not null,
    * [ ] mileage       int not null,
    * [ ] transmission  varchar(30) not null,
    * [ ] makeId        int not null,
    * [ ] constraint fk_cars_make foreign key (makeId) references make(makeId)   
  * [ ] create table makes
    * [ ] makeId         int primary key auto_increment,
    * [ ] makeName       varchar(20) not null,
    * [ ] modelId        int not null,
    * [ ] constraint fk_make_model foreign key (modelId) references model(modelId)   
  * [ ] create table models
    * [ ] modelId        int primary key auto_increment,
    * [ ] modelName      varchar(20) not null,
    * [ ] modelYear      year(date) not null,
  * [ ] create table roles
    * [ ] roleId        int primay key auto_increment
    * [ ] roleName      varchar(20) not null unique
  * [ ] create table userroles
    * [ ] userId        int not null,
    * [ ] roleId        int not null,
    * [ ] constraint pk_userroles (userId, roleId),
    * [ ] constraint fk_users_userroles foreign key (userId) references users(userId)
    * [ ] constraint fk_roles_userroles foreign key (roleId) references roles(roleId)
  * [ ] insert into users (username, password) values ('bob', '$2a$12$HqaU3VlN09ufZ60R8VrLHuIX8H6b1iFDA9AG./vzThpIzhxEIF8nC');   -- pw is password
  * [ ] insert into users (username, password) values ('june', '$2a$12$k2TB.cQ1TLHLOYn.pbbiTuQ5HoUxozWkl.ZgFZ.9eioAeMxndT5AS');  -- pw is admin-password
  * [ ] insert into roles (roleName) VALUES ('AUTHOR'), ('ADMIN');
  * [ ] insert into userroles (userId, roleId) VALUES (1,1), (2,2);
  * [ ] <b>CHANGE SAMPLE DATA TO MATCH LISTINGS INSTEAD OF listingS</b>
  * [ ] insert into listings (listingText, authorId, isPublic, createDate) values ('this is a private listing', 1, 0, '2020-04-06'), ('this is a public listing', 2, 1, '2020-04-05');
  * [ ] generate reset stored procedure in db (set_known_good_state)
    * [ ] delete from userroles;
    * [ ] delete from users;
    * [ ] alter table users auto_increment = 1;
    * [ ] delete from roles;
    * [ ] alter table roles auto_increment = 1;
    * [ ] delete from listings;
    * [ ] alter table listings auto_increment = 1;
    * [ ] (copy all inserts from prod)
  * [ ] at end of test schema call set_known_good_state();

* [ ] Create React Front-End
  * [ ] From the terminal, inside of your Java application
    * [ ] `npx create-react-app client`
    * [ ] `cd client`
    * [ ] `code .` [optional - open in VSCode]
  * [ ] Delete cruft
    * [ ] ./public/favicon.ico
    * [ ] ./public/logo192.png
    * [ ] ./public/logo512.png
    * [ ] ./public/manifest.json
    * [ ] ./public/robots.txt
    * [ ] ./src/App.css
    * [ ] ./src/App.test.js
    * [ ] ./src/logo.svg
    * [ ] ./src/reportWebVitals.js
    * [ ] ./src/setupTests.js
    * [ ] Update ./public/index.html
      * [ ] From default file, delete:
        * [ ] Lines 4-26
        * [ ] Change Title to `Smokingtires`
        * [ ] Delete any additional comments here
    * [ ] Update ./src/App.js
      * [ ] From default file, delete:
        * [ ] Lines 7-20
        * [ ] Lines 1-2
    * [ ] Update ./src/index.css\
      * [ ] Trashcan it all
     * [ ] Update ./src/index.js
      * [ ] From default file, delete:
        * [ ] Lines 14-17
        * [ ] Lines 5
  * [ ] Add additional dependencies
    * [ ] `npm i react-router-dom `
  * [ ] Create components (indents below indicate parent-child relations)
    * [ ] Nav Component
    * [ ] Login Component
    * [ ] Home Component - welcoming and show top listings based on view count (5 listings)
      * [ ] Welcome Component - nested inside Home
    * [ ] Listings (container) Component
      * [ ] Listing Component
      * [ ] Delete Component
    * [ ] AddListing Component
  * [ ] Add react-router to our project
    * [ ] At the top of index.js
      * [ ] `import { BrowserRouter } from 'react-router-dom';`
      * [ ] Change `<React.StrictMode>` to `<BrowserRouter>`
      * [ ] Change `</React.StrictMode>` to `</BrowserRouter>`
  * [ ] Build out base Home component
    * [ ] Functional component, don't forget to export!
  * [ ] Build out base Welcome component
    * [ ] Functional component, don't forget to export!
  * [ ] Add `<Home />` to App.js
    * [ ] `import Home from "./Home";`
    * [ ] Add flavor-text to ground ourselves
  * [ ] Add `<Welcome />` to Home.js
    * [ ] `import Welcome from "./Welcome";`
    * [ ] Add flavor-text to ground ourselves
  * [ ] Add `<Nav />` to App.js
    * [ ] `import Nav from "./Nav";`
    * [ ] Add flavor-text to ground ourselves
  * [ ] Begin implementing Routes in App.js
    * [ ] `import { Routes, Route } from 'react-router-dom';`
    * [ ] `<Routes>`
      * [ ] `<Route path="/" element={<Home />} />`
      * [ ] `// ^^ Home Page Route, at base dot-com URL`
    * [ ] `</Routes>`
  * [ ] Begin implementing Links in Nav.js
    * [ ] `import { Link } from 'react-router-dom';`
    * [ ] `<Link to="/">Home</Link>`
  * [ ] In Listings.js...
    * [ ] Create State to store all listings
      * [ ] `import { useState } from 'react';`
        * [ ] `const [listings, setListings] = useState([]);`
    * [ ] Implement `useEffect()` hook for setting state on fetch
      * [ ] `import { useState, useEffect } from 'react';`
      * [ ] `useEffect(() => {`
        * [ ] Use Fetch API to retrieve our public listings
          * [ ] Verify CORS is open in your ListingController (Java)
            * [ ] `@CrossOrigin(origins = {"http://localhost:3000"})`
          * [ ] `fetch("http://localhost:8080/api/listing")`
          * [ ] `.then(response => {`
              * [ ] `if (response.status === 200) {`
                * [ ] `return response.json() `
              * [ ] `} else {`
                * [ ] `alert("Something went wrong when fetching")`
              * [ ] `}`
          * [ ] `})`
          * [ ] `.then(listingsData => setListings(listingsData))`
          * [ ] `.catch(rejection => alert("Failure: " + rejection.status))`
      * [ ] `}, [])`
    * [ ] `import Listing from './Listing'`
    * [ ] Implement a `<Listing />` factory function
      * [ ] `function listingFactory() {`
        * [ ] `return listings.map(listing => <Listing key={listing.listingId} listingObj={listing} />);` 
      * [ ] `}`
      * [ ] Call function inside of the return for `<Listing />`
        * [ ] `return (`
          * [ ] `<>`
            * [ ] `{listingFactory()}`
          * [ ] `</>`
        * [ ] `)`
    * [ ] Build out the base `<Listing />` component
      * [ ] Functional component, don't forget to export
    * [ ] Use `props.listingObj` to access the listing and display in Listing.js
      * [ ] Destructure the properties of my listingObj into variables
        * [ ] const { <b> ALL LISTING PROPERTIES </b> } = props.listingObj;
        * [ ] Build HTML/JSX structure to display data <b>CHANGE DISPLAYED DATA TO MATCH LISTING PROPERTIES UNDER HERE</b> to return
          * [ ] `<div className="listing-item">`
            * [ ] `<h3>User Id: {userId}</h3>`
            * [ ] `<p>Created: {createDate}</p>`
            * [ ] `<p>Text: {text}</p>`
          * [ ] `</div>`
    * [ ] Update index.css with Dev-CSS to help visualize
      * [ ] `.listing-item { `
        * [ ] `border: 1px black solid;`
        * [ ] `padding: 20px;`
        * [ ] `margin-bottom: 30px;`
      * [ ] `}`
  * [ ] Implement useContext hook
    * [ ] Create AuthContext.js
      * [ ] import { createContext } from 'react';
      * [ ] const AuthContext = createContext();
      * [ ] export default AuthContext
  * [ ] In App.js, implement Context
    * [ ] `import { useState } from 'react';`
    * [ ] `import AuthContext from "./AuthContext";`
    * [ ] `const [user, setUser] = useState(null);`
    * [ ] Inside of the return
      * [ ] Before rendering any other components, encapusulate with:
        * [ ] `<AuthContext.Provider value={[user, setUser]}>`
          * [ ] `(everything else you already had here)`
        * [ ] `</AuthContext.Provider>`
  * [ ] Verify CORS is handled in AuthController (Java)
    * [ ] `@CrossOrigin(origins = {"http://localhost:3000"})`
  * [ ] Build out Login component
    * [ ] In terminal: `npm i jwt-decode`
    * [ ] `import { useState, useContext } from "react";`
    * [ ] `import { useNavigate } from "react-router-dom";`
    * [ ] `import jwtDecode from "jwt-decode";`
    * [ ] `import AuthContext from "./AuthContext";`
    * [ ] const [username, setUsername] = useState("");
    * [ ] const [password, setPassword] = useState("");
    * [ ] const [user, setUser] = useContext(AuthContext);
    * [ ] const navigate = useNavigate();
    * [ ] Build out a form for logging in
      * [ ] `<form onSubmit={submitHandler}>`
        * [ ] `<label>Username:</label><br />`
        * [ ] `<input onChange={event => setUsername(event.target.value)}></input><br /><br />`
        * [ ] `<label>Password:</label><br />`
        * [ ] `<input type="password" onChange={event => setPassword(event.target.value)}></input><br /><br />`
        * [ ] `<button>Submit</button>`
      * [ ] `</form>`
    * Create submit handler for form
      * [ ] `function submitHandler(event) {`
        * [ ] `event.preventDefault()`
        * [ ] `fetch("http://localhost:8080/api/security/login", {`
          * [ ] `method: "POST",`
          * [ ] `headers: {`
            * [ ] `"Content-Type": "application/json"`
          * [ ] ` },`
          * [ ] `body: JSON.stringify({`
            * [ ] `username, password`
          * [ ] `})`
        * [ ] `})`
        * [ ] `.then(response => {`
          * [ ] `if (response.status === 200) {`
            * [ ] `const { jwt_token } = response.json()`
            * [ ] `localStorage.setItem("token", jwt_token)`
            * [ ] `setUser({user: jwtDecode(jwt_token)})`
            * [ ] `navigate("/")`
          * [ ] `} else {`
            * [ ] `alert("Something bad");`
          * [ ] `}`
        * [ ] `})`
        * [ ] `.catch(rejection => alert(rejection))`
      * [ ] `}`
    * [ ] Update App.js with new Login route
      * [ ] `import Login from "./Login";`
      * [ ] `<Route path="/login" element={<Login />} />`
    * [ ] Update Nav.js with new Login link
      * [ ] `import { useConte  xt } from 'react';`
      * [ ] `import AuthContext from './AuthContext';`
      * [ ] `const [userStatus, setUserStatus] = useContext(AuthContext);`
  