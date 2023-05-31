
-- *****  insert and uncomment the below SET statements before running the SQL script so
-- so that the data will be inserted into the correct db & schema

-- sets the database to use
-- SET search_path TO postgres;

-- sets the schema to insert data into
-- SET SCHEMA 'your schema name';

--roles
INSERT INTO ROLES (id, name) VALUES ('be06d9a6-b066-40a0-9f15-1d4a42b7abf4', 'USER');
INSERT INTO ROLES (id, name) VALUES ('224d8726-78cf-44a1-8ee0-054cdb79ecf5', 'ADMIN');

--non-admin testers
INSERT INTO USERS (id, username, password, roles_id) VALUES ('8a05c484-e1a8-426b-bd35-2dfd28861efb', 'tester01', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('3c9fffda-2f22-4835-a3d9-631972aca2ce', 'tester02', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('1138fcf4-2057-4c89-b226-2da3101b6fa8', 'tester03', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('11d4c163-598b-45f2-a829-6636fc8239a8', 'tester04', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );

--admin testers
INSERT INTO USERS (id, username, password, roles_id) VALUES ('62b34954-a090-4d1a-b189-a07b032830d8', 'admin01', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', '224d8726-78cf-44a1-8ee0-054cdb79ecf5' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('25c28f09-ef02-4f55-acd4-946272c7a1ae', 'admin02', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', '224d8726-78cf-44a1-8ee0-054cdb79ecf5' );

--categories
INSERT INTO DEPARTMENTS (id, name) VALUES ('ad3b2d7b-aed6-4189-8874-95dac15f5b2f', 'Outfront House of Steak');
INSERT INTO DEPARTMENTS (id, name) VALUES ('4e3cd06a-870e-4885-b459-05024fad2fb3', 'We Do Not Sell Pizza');
INSERT INTO DEPARTMENTS (id, name) VALUES ('0d709a47-9f1c-4583-bda6-dea6fa6ba168', 'McDurnguls');
INSERT INTO DEPARTMENTS (id, name) VALUES ('a938696a-8bf3-43dd-95be-89e055860f63', 'Oops! All Meatballs');

--CategoryA products
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('12345678-f2e3-404e-9e80-5f4cb454c5fd', '10oz Ribeye', '15.99', '14', 'ad3b2d7b-aed6-4189-8874-95dac15f5b2f');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('23456789-f2e3-404e-9e80-5f4cb454c5fd', 'Surf-n-Turf', '22.49', '7', 'ad3b2d7b-aed6-4189-8874-95dac15f5b2f');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('34567890-f2e3-404e-9e80-5f4cb454c5fd', 'Half-Rack of Ribs', '14.72', '24', 'ad3b2d7b-aed6-4189-8874-95dac15f5b2f');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('45678901-f2e3-404e-9e80-5f4cb454c5fd', 'Full-Rack of Ribs', '24.99', '12', 'ad3b2d7b-aed6-4189-8874-95dac15f5b2f');

--We Don't Sell Pizza
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('12345678-e770-4415-b618-abe73d83bfcd', 'Spaghetti', '2.99', '14', '4e3cd06a-870e-4885-b459-05024fad2fb3');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('23456789-e770-4415-b618-abe73d83bfcd', 'Fettuccine Alfredo', '2.99', '16', '4e3cd06a-870e-4885-b459-05024fad2fb3');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('34567890-e770-4415-b618-abe73d83bfcd', 'Linguini', '2.99', '15', '4e3cd06a-870e-4885-b459-05024fad2fb3');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('87654321-e770-4415-b618-abe73d83bfcd', 'Chicken Parmesan', '2.99', '10', '4e3cd06a-870e-4885-b459-05024fad2fb3');

--CategoryC products
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('12345678-1d96-4bf0-b116-5ace24bfcc25', 'Single McBorger', '5.99', '31', '0d709a47-9f1c-4583-bda6-dea6fa6ba168');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('87654321-1d96-4bf0-b116-5ace24bfcc25', 'Double McBorger', '7.99', '42', '0d709a47-9f1c-4583-bda6-dea6fa6ba168');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('1s3r4r5t-1d96-4bf0-b116-5ace24bfcc25', 'McRibbed', '5.99', '4', '0d709a47-9f1c-4583-bda6-dea6fa6ba168');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('6fswrf3f-1d96-4bf0-b116-5ace24bfcc25', 'McNuggiez', '3.99', '15', '0d709a47-9f1c-4583-bda6-dea6fa6ba168');

--CategoryD products
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('263ds2ws-02f8-4a17-9686-edd170b540a6', 'MeatballA', '3.99', '10', 'a938696a-8bf3-43dd-95be-89e055860f63');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('h64edh64-02f8-4a17-9686-edd170b540a6', 'MeatballB', '5.99', '10', 'a938696a-8bf3-43dd-95be-89e055860f63');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('7tds2567-02f8-4a17-9686-edd170b540a6', 'MeatballC', '9.99', '10', 'a938696a-8bf3-43dd-95be-89e055860f63');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('11223344-02f8-4a17-9686-edd170b540a6', 'TotallyNotAnotherMeatball', '14.99', '10', 'a938696a-8bf3-43dd-95be-89e055860f63');

--Pending Orders
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('d24bd372-9762-4362-be0b-9632aa11345c', 'f01960f5-84d9-4710-9e2c-2cec81fca136', '0', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '34567890-f2e3-404e-9e80-5f4cb454c5fd');
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('649c56a9-7935-4036-be1e-687c3aa36871', 'f01960f5-84d9-4710-9e2c-2cec81fca136', '0', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '7tds2567-02f8-4a17-9686-edd170b540a6');
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('105e51c3-7cc5-4989-ab62-8a564c09fc15', 'f01960f5-84d9-4710-9e2c-2cec81fca136', '0', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '23456789-f2e3-404e-9e80-5f4cb454c5fd');

--Completed Orders
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('c7466de0-f7da-4c05-978e-f38a734b0c49', '639b6c01-87ca-4224-80ad-e6133b1f5d5e', '2', '5', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '1s3r4r5t-1d96-4bf0-b116-5ace24bfcc25');
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('06783f64-8ecb-43ed-bfba-6068ce9b47ce', '639b6c01-87ca-4224-80ad-e6133b1f5d5e', '2', '5', '8a05c484-e1a8-426b-bd35-2dfd28861efb', 'h64edh64-02f8-4a17-9686-edd170b540a6');
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('9f705ac3-2156-40dd-a196-6f8090461a9a', '639b6c01-87ca-4224-80ad-e6133b1f5d5e', '2', '5', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '34567890-e770-4415-b618-abe73d83bfcd');

--reviews
INSERT INTO reviews (id, comment, rating, user_id, product_id) VALUES ('105e41c3-7cc5-4989-6832-8a564c09fc15', 'They really do not sell pizza so I got a spaghetti', '4', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '12345678-e770-4415-b618-abe73d83bfcd');
INSERT INTO reviews (id, comment, rating, user_id, product_id) VALUES ('125e41c3-7cc5-4989-6832-8a564c09fc15', 'Asked the manager why he no sell pizza. He threw spaghetti at me. Free food bro.', '5', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '12345678-e770-4415-b618-abe73d83bfcd');
INSERT INTO reviews (id, comment, rating, user_id, product_id) VALUES ('145e41c3-7cc5-4989-6832-8a564c09fc15', 'It was a meatball.', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '11223344-02f8-4a17-9686-edd170b540a6');
INSERT INTO reviews (id, comment, rating, user_id, product_id) VALUES ('105e4163-7cc5-4989-6832-8a564c09fc15', 'Tastes like safe off-branding', '3', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '1s3r4r5t-1d96-4bf0-b116-5ace24bfcc25');
INSERT INTO reviews (id, comment, rating, user_id, product_id) VALUES ('10xe41c3-7cc5-4989-6832-8a564c09fc15', 'They never took me surfing', '2', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '23456789-f2e3-404e-9e80-5f4cb454c5fd');
