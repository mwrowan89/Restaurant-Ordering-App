-- Create the database
CREATE DATABASE IF NOT EXISTS daamdb;

-- Use the database
USE daamdb;

-- Drop tables if they exist to avoid conflicts (reverse order of creation to respect foreign keys)
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS menuitems;
DROP TABLE IF EXISTS films;

-- Create the users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first VARCHAR(255) NOT NULL,
    last VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    image_url VARCHAR(255),
    pan VARCHAR(255),
    expiry_month INT,
    expiry_year INT,
    roles VARCHAR(255)
);

-- Create the user_roles table
CREATE TABLE user_roles (
    user_id INT,
    role VARCHAR(50),
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create the menuitems table
CREATE TABLE menuitems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    category VARCHAR(50),
    price DECIMAL(10,2) NOT NULL,
    imageurl VARCHAR(255),
    available BOOLEAN NOT NULL
);

-- Create the orders table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userid INT,
    ordertime VARCHAR(255),
    pickuptime VARCHAR(255),
    area VARCHAR(255),
    location VARCHAR(255),
    tax DECIMAL(10,2),
    tip DECIMAL(10,2),
    pan VARCHAR(255),
    expiry_month INT,
    expiry_year INT,
    status VARCHAR(50),
    FOREIGN KEY (userid) REFERENCES users(id)
);

-- Create the items table
CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderid INT NOT NULL,
    itemid INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    notes TEXT,
    FOREIGN KEY (orderid) REFERENCES orders(id),
    FOREIGN KEY (itemid) REFERENCES menuitems(id)
);

-- Create the films table
CREATE TABLE films (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    homepage VARCHAR(255),
    overview TEXT,
    posterpath VARCHAR(255),
    runtime INT,
    tagline VARCHAR(255),
    popularity DECIMAL(10,2),
    imdbid VARCHAR(20),
    voteaverage DECIMAL(10,2),
    votecount INT
);
