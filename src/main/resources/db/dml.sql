
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