#!/bin/bash

# Script to initialize the restaurant database
echo "Restaurant Database Initialization Script"
echo "----------------------------------------"
echo "Choose an initialization method:"
echo "1. Use Docker Compose (recommended)"
echo "2. Use init_mysql.sh script (direct to MySQL)"
read -p "Enter your choice (1-2): " choice

case $choice in
  1)
    echo "Initializing database with Docker Compose..."
    
    # Check if containers are running and stop them
    echo "Stopping existing containers..."
    docker compose down
    
    # Start the MariaDB container
    echo "Starting MariaDB container..."
    docker compose up -d mariadb
    
    echo "Waiting for database to initialize (30 seconds)..."
    sleep 30
    
    echo "Database initialization complete!"
    echo "You can now start your Spring Boot application or run: docker compose up -d back-end"
    ;;
    
  2)
    echo "Initializing database with init_mysql.sh script..."
    cd back-end/database
    chmod +x init_mysql.sh
    ./init_mysql.sh
    cd ../..
    ;;
    
  *)
    echo "Invalid choice. Exiting."
    exit 1
    ;;
esac

echo "Database initialization process completed!"
