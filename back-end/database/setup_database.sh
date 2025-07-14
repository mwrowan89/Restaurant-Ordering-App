#!/bin/bash

# Database connection parameters
MYSQL_USER="root"
MYSQL_HOST="localhost"

# Ask for MySQL password
read -sp "Enter MySQL root password: " MYSQL_PASSWORD
echo ""

# Path to SQL files
CREATE_DB_SQL="./src/database_scripts/create_db.sql"
DATA_SQL="./src/main/resources/data.sql"

# Display informational messages
echo "Setting up database for Restaurant Ordering App..."

# Execute create_db.sql
echo "Creating database and tables..."
mysql -u $MYSQL_USER -p"$MYSQL_PASSWORD" -h $MYSQL_HOST < $CREATE_DB_SQL

# Check if database creation was successful
if [ $? -eq 0 ]; then
    echo "Database and tables created successfully."
    
    # Execute data.sql to populate the tables
    echo "Populating tables with initial data..."
    mysql -u $MYSQL_USER -p"$MYSQL_PASSWORD" -h $MYSQL_HOST daamdb < $DATA_SQL
    
    if [ $? -eq 0 ]; then
        echo "Data imported successfully."
        echo "Database setup completed!"
    else
        echo "Error importing data. Please check the data.sql file and your MySQL connection."
        exit 1
    fi
else
    echo "Error creating database. Please check the create_db.sql file and your MySQL connection."
    exit 1
fi
