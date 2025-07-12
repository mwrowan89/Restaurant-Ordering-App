#!/bin/bash

# This script initializes the MySQL database for the Restaurant Ordering App

# Set MySQL connection parameters
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USER=root
MYSQL_PASSWORD=secret123
DATABASE_NAME=daamdb

echo "Creating database schema for Restaurant Ordering App..."

# Create database if it doesn't exist
mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER -p$MYSQL_PASSWORD <<EOF
CREATE DATABASE IF NOT EXISTS $DATABASE_NAME;
USE $DATABASE_NAME;

-- Create Users table
CREATE TABLE IF NOT EXISTS users (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  USERNAME VARCHAR(50) NOT NULL,
  PASSWORD VARCHAR(100) NOT NULL,
  FIRST VARCHAR(50),
  LAST VARCHAR(50),
  PHONE VARCHAR(20),
  EMAIL VARCHAR(100),
  IMAGE_URL VARCHAR(255),
  PAN VARCHAR(20),
  EXPIRY_MONTH INT,
  EXPIRY_YEAR INT,
  ROLES VARCHAR(100) NOT NULL
);

-- Create MenuItems table
CREATE TABLE IF NOT EXISTS menuitems (
  id VARCHAR(10) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  category VARCHAR(50),
  price DECIMAL(10,2) NOT NULL,
  imageurl VARCHAR(255),
  available BOOLEAN DEFAULT TRUE
);

-- Create Orders table
CREATE TABLE IF NOT EXISTS orders (
  id VARCHAR(10) PRIMARY KEY,
  userid VARCHAR(10),
  ordertime VARCHAR(20),
  pickuptime VARCHAR(20),
  area VARCHAR(50),
  location VARCHAR(50),
  tax DECIMAL(10,2),
  tip DECIMAL(10,2),
  pan VARCHAR(20),
  expiry_month INT,
  expiry_year INT,
  status VARCHAR(20)
);

-- Create Items table
CREATE TABLE IF NOT EXISTS items (
  id VARCHAR(10) PRIMARY KEY,
  orderid VARCHAR(10),
  itemid VARCHAR(10),
  price DECIMAL(10,2),
  firstname VARCHAR(50),
  notes TEXT,
  FOREIGN KEY (orderid) REFERENCES orders(id)
);

-- Create Films table
CREATE TABLE IF NOT EXISTS films (
  id VARCHAR(10) PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
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
EOF

echo "Database schema created successfully!"

# Import data from data.sql
echo "Loading data from data.sql..."
mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER -p$MYSQL_PASSWORD $DATABASE_NAME < ../src/main/resources/data.sql

echo "Data loaded successfully!"
