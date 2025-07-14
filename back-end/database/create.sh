#!/bin/bash

echo "Setting up MySQL database for the Restaurant Ordering App..."
echo "This script will create the database and populate it with initial data."

# Call the setup_database.sh script
BASEDIR=$(dirname $0)
bash "$BASEDIR/setup_database.sh"

echo "Database setup complete!"