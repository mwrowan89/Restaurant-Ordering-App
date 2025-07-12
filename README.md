# Restaurant Ordering Application

This project uses Docker to set up a development environment for the restaurant ordering application.

## Docker Setup Instructions

### Prerequisites
- Docker and Docker Compose installed on your machine
- JDK 17+ for running the backend locally
- Node.js for running the frontend locally

### Starting the Environment

1. Start the entire stack:
   ```bash
   docker-compose up -d
   ```

2. Start only the database:
   ```bash
   docker-compose up -d mariadb
   ```

3. Check if the containers are running:
   ```bash
   docker-compose ps
   ```

### Database Access

The MariaDB database is accessible at:
- Host: localhost
- Port: 3306
- User: root
- Password: secret123
- Database: daamdb

You can connect to the database using:
```bash
mysql -h localhost -P 3306 -u root -p daamdb
# Enter password when prompted: secret123
```

### Backend Development

The backend is configured to connect to the database automatically. If you're running the backend outside Docker, use these connection parameters in your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/daamdb
spring.datasource.username=root
spring.datasource.password=secret123
```

### Data Initialization

The database is initialized automatically with:
- Schema creation via Hibernate (spring.jpa.hibernate.ddl-auto=update)
- Data from data.sql loaded at startup

### Troubleshooting

If you encounter issues:

1. Check container logs:
   ```bash
   docker-compose logs mariadb
   docker-compose logs back-end
   ```

2. Restart a service:
   ```bash
   docker-compose restart mariadb
   ```

3. Reset the entire environment:
   ```bash
   docker-compose down -v
   docker-compose up -d
   ```

### Running the Backend Manually

If you prefer to run the backend outside of Docker:

1. Ensure the database is running:
   ```bash
   docker-compose up -d mariadb
   ```

2. Navigate to the back-end directory:
   ```bash
   cd back-end
   ```

3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
