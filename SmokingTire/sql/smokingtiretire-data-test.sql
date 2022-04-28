drop database if exists smokingtires_test;

create database smokingtires_test;

use smokingtires_test;

-- create tables and relationships

create table users(
userId        int primary key auto_increment,
username      varchar(300) not null unique,
`password`      varchar(2048) not null

);


create table models(
modelId        int primary key auto_increment,
modelName      varchar(20) not null,
modelYear      date not null
    );



create table makes(
makeId         int primary key auto_increment,
makeName       varchar(20) not null,
modelId        int not null,
constraint fk_make_model foreign key (modelId) references models(modelId)

);




create table cars(
carId         int primary key auto_increment,

horsepower    int not null,
drivetrain    varchar(20) not null,
chassis       varchar(50) not null,
transmission  varchar(30) not null,
makeId        int not null,
constraint fk_cars_make foreign key (makeId) references makes(makeId)
);

create table listings(
listingId        int primary key auto_increment,
listingText      text not null,
userId           int not null,
carId            int not null,
createDate       date not null,
views            int not null,
mileage       int not null,
price         int not null,
constraint fk_listings_users foreign key (userId) references users(userId),
constraint fk_listings_cars foreign key (carId) references cars(carId)

);





create table roles(
roleId        int primary key auto_increment,
roleName      varchar(20) not null unique
    
    );
    
create table userroles(
userId        int not null,
roleId        int not null,
constraint pk_userroles primary key (userId, roleId),
constraint fk_users_userroles foreign key (userId) references users(userId),
constraint fk_roles_userroles foreign key (roleId) references roles(roleId)
    
    );
    

 
    
    
    delimiter //
    
    create procedure set_known_good_state()
    begin
    delete from userroles;
    delete from users;
    alter table users auto_increment = 1;
    delete from roles;
    alter table roles auto_increment = 1;
    delete from listings;
    alter table listings auto_increment = 1;
    
    
    insert into users (username, password) 
 values 
 ('bob', '$2a$12$HqaU3VlN09ufZ60R8VrLHuIX8H6b1iFDA9AG./vzThpIzhxEIF8nC');   -- pw is password
 
insert into users (username, password) 
values 
('june', '$2a$12$k2TB.cQ1TLHLOYn.pbbiTuQ5HoUxozWkl.ZgFZ.9eioAeMxndT5AS');
  -- pw is admin-password
  
insert into roles (roleName) 
VALUES 
('AUTHOR'), ('ADMIN');

insert into userroles (userId, roleId) 
VALUES 
(1,1), (2,2); 
  
insert into listings (listingText, authorId, isPublic, createDate) 
values 
('this is a private listing', 1, 0, '2020-04-06'), ('this is a public listing', 2, 1, '2020-04-05');
    
    
    end //
    
    delimiter ;

call set_known_good_state();