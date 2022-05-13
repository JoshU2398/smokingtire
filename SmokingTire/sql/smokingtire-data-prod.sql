drop database if exists smokingtires_prod;

create database smokingtires_prod;

use smokingtires_prod;

-- create tables and relationships

create table users(
userId        int primary key auto_increment,
username      varchar(300) not null unique,
`password`      varchar(2048) not null
);

create table makes(
makeId         int primary key auto_increment,
makeName       varchar(20) not null
);

create table models(
modelId        int primary key auto_increment,
modelName      varchar(20) not null,
modelYear      year not null,
makeId        int not null,
constraint fk_model_make foreign key (makeId) references makes(makeId)
);
    
create table images(
imageUrl 		varchar(100) primary key,
modelId 		int not null,
constraint fk_images_modelId foreign key (modelId) references models(modelId)
);


create table cars(
carId         int primary key auto_increment,
horsepower    int not null,
drivetrain    varchar(20) not null,
chassis       varchar(50) not null,
transmission  varchar(30) not null,
modelId        int not null,
constraint fk_cars_model foreign key (modelId) references models(modelId)
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
isAvailable	  boolean not null,
imageUrl 	  varchar(100) not null,
constraint fk_listings_users foreign key (userId) references users(userId),
constraint fk_listings_cars foreign key (carId) references cars(carId),
constraint fk_listings_images foreign key (imageUrl) references images(imageUrl)

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
    
insert into users (username, password) 
 values 
('bob', '$2a$12$HqaU3VlN09ufZ60R8VrLHuIX8H6b1iFDA9AG./vzThpIzhxEIF8nC'), -- pw is password
('june', '$2a$12$k2TB.cQ1TLHLOYn.pbbiTuQ5HoUxozWkl.ZgFZ.9eioAeMxndT5AS'), -- pw is admin-password
('eirik', '$2a$12$4m2LLyJ9Bo8lt3QQdU.K/ONLOGdqioMzX5K0DMnLj9oLyYZy0bxxC'),
('josh', '$2a$12$oFARCoqVpC.2o4CoektyH.7YB8Ri2LmPEOJqzTLoJKr52FOaIy5.e'),
('mehran', '$2a$12$nHqqmt0vDufjuQYnqN3m1eRjmnDa7uiR1/1mHfQt8Gc6aPKVo6Ykq');
  
insert into roles (roleName) 
VALUES 
('USER'), ('ADMIN');

insert into userroles (userId, roleId) 
VALUES 
(1,1), (2,2), (3,1), (4,1), (5,1); 
  
insert into makes(
makeName
)
values
('Toyota'),
('Dodge'),
('Ford'),
('Chevrolet');

insert into models(
modelName,
modelYear,
makeId
)
values
('Supra', '1998', 1),
('Tacoma', '2022', 1),
('Viper', '2004', 2),
('Challenger', '1970', 2),
('Mustang', '2010', 3),
('GT', '2006', 3),
('Z06', '2023', 4),
('Camaro', '1975', 4);

insert into images(imageUrl, modelId) 
values 
	('1998Viper.jpg',3), ('supra.jpg',1), ('tacoma.jpg',2),
    ('challenger.jpg',4), ('mustang.jpg',5), ('gt.jpg',6),
    ('Z06.png',7),('camaro.jpg',8);

insert into cars(
horsepower,
drivetrain,
chassis,
transmission,
modelId
)
values
(159, '4WD', 'truck', 'automatic', 2),
(500, 'rear-wheel drive', 'roadster', 'manual', 3),
(276, 'rear-wheel drive', 'coupe', 'manual', 1),
(425, 'rear-wheel drive', 'coupe', 'manual', 4),
(325, 'rear-wheel drive', 'coupe', 'manual', 5),
(550, 'rear-wheel drive', 'sports-car', 'manual', 6),
(670, 'rear-wheel drive', 'sports-car', 'manual', 7),
(150, 'rear-wheel drive', 'coupe', 'manual', 8);


insert into listings (
listingText,
userId,
carId,
createDate,
views,
mileage,
price,
isAvailable,
imageUrl
) 
values 
('This is a Toyota Tacoma', 3, 1, '2022-03-21', 523, 25000, 30000, 1, 'tacoma.jpg'),
('This is a Toyota Supra', 1, 3, '2020-04-06', 6523, 20000, 70000, 0, 'supra.jpg'), 
('This is a Dodge Viper', 1, 2, '2020-04-05', 8792, 2000, 120000, 1, '1998Viper.jpg'),
('This is a Dodge Challenger', 3, 4, '2022-05-01', 31, 50000, 125000, 1, 'challenger.jpg'),
('This is a Ford Mustang', 4, 5, '2021-12-01', 971, 50000, 12000, 1, 'mustang.jpg'),
('This is a Ford GT', 5, 6, '2022-01-01', 1927, 50000, 100000, 1, 'gt.jpg'),
('This is a Chevrolet Corvette Z06', 5, 7, '2022-01-01', 197, 1000, 140000, 1, 'Z06.png'),
('This is a Chevrolet Camaro', 4, 8, '2021-11-21', 2054, 10000, 145000, 1, 'camaro.jpg');
    