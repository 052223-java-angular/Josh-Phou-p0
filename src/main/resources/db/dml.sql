
-- *****  insert and uncomment the below SET statements before running the SQL script so
-- so that the data will be inserted into the correct db & schema

-- sets the database to use
-- SET search_path TO postgres;

-- sets the schema to insert data into
-- SET SCHEMA 'your schema name';

INSERT INTO ROLES (id, name) VALUES ('be06d9a6-b066-40a0-9f15-1d4a42b7abf4', 'USER');
INSERT INTO ROLES (id, name) VALUES ('224d8726-78cf-44a1-8ee0-054cdb79ecf5', 'ADMIN');

INSERT INTO USERS (id, username, password, roles_id) VALUES ('8a05c484-e1a8-426b-bd35-2dfd28861efb', 'tester01', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('3c9fffda-2f22-4835-a3d9-631972aca2ce', 'tester02', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('1138fcf4-2057-4c89-b226-2da3101b6fa8', 'tester03', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('11d4c163-598b-45f2-a829-6636fc8239a8', 'tester04', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', 'be06d9a6-b066-40a0-9f15-1d4a42b7abf4' );

INSERT INTO USERS (id, username, password, roles_id) VALUES ('62b34954-a090-4d1a-b189-a07b032830d8', 'admin01', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', '224d8726-78cf-44a1-8ee0-054cdb79ecf5' );
INSERT INTO USERS (id, username, password, roles_id) VALUES ('25c28f09-ef02-4f55-acd4-946272c7a1ae', 'admin02', '$2a$10$48v6m6vF873uSR8Djs7CyOWToMXaARvKK7G4GFRkdV32SBXqmKhpK', '224d8726-78cf-44a1-8ee0-054cdb79ecf5' );

INSERT INTO DEPARTMENTS (id, name) VALUES ('ad3b2d7b-aed6-4189-8874-95dac15f5b2f', 'CategoryA');
INSERT INTO DEPARTMENTS (id, name) VALUES ('4e3cd06a-870e-4885-b459-05024fad2fb3', 'CategoryB');
INSERT INTO DEPARTMENTS (id, name) VALUES ('0d709a47-9f1c-4583-bda6-dea6fa6ba168', 'CategoryC');
INSERT INTO DEPARTMENTS (id, name) VALUES ('a938696a-8bf3-43dd-95be-89e055860f63', 'CategoryD');
INSERT INTO DEPARTMENTS (id, name) VALUES ('2bce5d26-f2a7-4192-8923-c7fb404bc75a', 'CategoryE');

INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('93657de5-f2e3-404e-9e80-5f4cb454c5fd', 'product00', '9.99', '10', 'ad3b2d7b-aed6-4189-8874-95dac15f5b2f');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('f1239cd0-e770-4415-b618-abe73d83bfcd', 'product10', '9.99', '10', '4e3cd06a-870e-4885-b459-05024fad2fb3');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('d1ab42b2-1d96-4bf0-b116-5ace24bfcc25', 'product20', '9.99', '10', '0d709a47-9f1c-4583-bda6-dea6fa6ba168');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('136edbe8-02f8-4a17-9686-edd170b540a6', 'product30', '9.99', '10', 'a938696a-8bf3-43dd-95be-89e055860f63');
INSERT INTO PRODUCTS (id, name, price, on_hand, departments_id) VALUES ('235d7907-d1c3-404f-9a23-a8ec572664ab', 'product40', '9.99', '10', '2bce5d26-f2a7-4192-8923-c7fb404bc75a');

-- Primary key needs to be unique, so there needs to be a column to store a cart / order_id in order to fetch all products belonging to the order
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('d24bd372-9762-4362-be0b-9632aa11345c', 'f01960f5-84d9-4710-9e2c-2cec81fca136', '0', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', '93657de5-f2e3-404e-9e80-5f4cb454c5fd');
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('649c56a9-7935-4036-be1e-687c3aa36871', 'f01960f5-84d9-4710-9e2c-2cec81fca136', '0', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', 'f1239cd0-e770-4415-b618-abe73d83bfcd');
INSERT INTO ORDERS (id, order_id, status, quantity, user_id, product_id) VALUES ('105e51c3-7cc5-4989-ab62-8a564c09fc15', 'f01960f5-84d9-4710-9e2c-2cec81fca136', '0', '1', '8a05c484-e1a8-426b-bd35-2dfd28861efb', 'd1ab42b2-1d96-4bf0-b116-5ace24bfcc25');
