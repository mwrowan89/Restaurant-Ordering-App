-- Schema for test database
-- This can be used to set up the test tables structure

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userid VARCHAR(255) NOT NULL,
    ordertime TIMESTAMP,
    pickuptime TIMESTAMP,
    area VARCHAR(255),
    location VARCHAR(255),
    tax DECIMAL(10, 2),
    tip DECIMAL(10, 2),
    pan VARCHAR(255),
    expiry_month INT,
    expiry_year INT,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS menuitems (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1500),
    category VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    imageurl VARCHAR(255),
    available BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderid VARCHAR(255) NOT NULL,
    itemid VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    notes VARCHAR(255),
    firstname VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    homepage VARCHAR(255),
    overview VARCHAR(1500),
    posterpath VARCHAR(255),
    runtime INT,
    tagline VARCHAR(255),
    popularity DECIMAL(10, 2),
    imdbid VARCHAR(255),
    voteaverage DECIMAL(10, 2),
    votecount INT
);
