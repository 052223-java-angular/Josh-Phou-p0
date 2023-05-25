DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS departments CASCADE;

CREATE TABLE users (
    id VARCHAR PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    roles_id VARCHAR NOT NULL,
    FOREIGN KEY (roles_id) REFERENCES roles (id)
);

CREATE TABLE roles (
    id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE orders (
    id VARCHAR PRIMARY KEY,
    status VARCHAR NOT NULL,
    quantity VARCHAR NOT NULL,
    user_id VARCHAR NOT NULL,
    product_id VARCHAR,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE products (
    id VARCHAR PRIMARY KEY,
    name VARCHAR,
    price VARCHAR,
    on_hand VARCHAR,
    departments_id VARCHAR,
    FOREIGN KEY (departments_id) REFERENCES departments (id)
);

CREATE TABLE reviews (
    id VARCHAR PRIMARY KEY,
    comment VARCHAR,
    rating int,
    user_id VARCHAR NOT NULL,
    product_id VARCHAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE departments (
    id VARCHAR PRIMARY KEY,
    name VARCHAR
);