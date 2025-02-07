# Minesweeper API

A secure Spring Boot REST service for managing Minesweeper games, user authentication, and game history tracking.

## Features

- User registration and authentication with custom DTO serialization
- Secure password handling
- Game history tracking and management
- HTTP Basic authentication with CSRF disabled for API usage
- Custom DTO mapping strategy for enhanced security
- Containerized deployment with Docker Compose
- MySQL database for persistent storage

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL 8.0
- Docker & Docker Compose
- Maven 3.8.4

## Project Structure

```
src/
├── main/
│   ├── java/com/minesweeper/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   └── EncoderConfig.java
│   │   ├── controller/
│   │   │   ├── GameHistoryController.java
│   │   │   ├── HealthController.java
│   │   │   └── UserController.java
│   │   ├── dto/
│   │   │   └── UserDTO.java
│   │   ├── model/
│   │   │   ├── GameHistory.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── GameHistoryRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/
│   │   │   └── UserDTOSerializer.java
│   │   ├── service/
│   │   │   ├── CustomUserDetailsService.java
│   │   │   ├── GameHistoryService.java
│   │   │   └── UserService.java
│   │   └── MinesweeperApiApplication.java
│   └── resources/
│       └── application.properties
├── Dockerfile
└── docker-compose.yml
```

## Database Configuration

The application uses MySQL 8.0 as its primary database. Database configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://db:3306/minesweeper?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging Configuration
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## Docker Setup

### Prerequisites
- Docker 20.10.x or higher
- Docker Compose v2.x or higher

### Dockerfile

```dockerfile
FROM maven:3.8.4-openjdk-17 as builder
LABEL authors="satish"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/minesweeperapi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.devtools.restart.enabled=true", "/app.jar"]
```

### Docker Compose Configuration

```yaml
version: '3.8'

services:
  api:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      db:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/minesweeper?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: minesweeper
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-p$$MYSQL_ROOT_PASSWORD"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  mysql_data:
```

### Running with Docker Compose

1. Build and start the containers:
```bash
docker-compose up --build
```

2. Stop the containers:
```bash
docker-compose down
```

3. View logs:
```bash
docker-compose logs -f
```

## API Examples

### User Registration

**Request:**
```http
POST /api/users/register
Content-Type: application/json

{
  "email": "player@example.com",
  "password": "secure123",
  "name": "Player One"
}
```

**Response:**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "email": "player@example.com",
  "name": "Player One"
}
```

### Start New Game

**Request:**
```http
POST /api/games
Content-Type: application/json
Authorization: Basic cGxheWVyQGV4YW1wbGUuY29tOnNlY3VyZTEyMw==

{
  "difficulty": "EASY",
  "gridSize": 8,
  "numberOfMines": 10
}
```

**Response:**
```json
{
  "gameId": "2023-05-07T41:44:28551",
  "status": "IN_PROGRESS",
  "difficulty": "EASY",
  "gridSize": 8,
  "numberOfMines": 10
}
```

### Get Game History

**Request:**
```http
GET /api/games-history
Authorization: Basic cGxheWVyQGV4YW1wbGUuY29tOnNlY3VyZTEyMw==
```

**Response:**
```json
[
  {
    "id": "1",
    "userId": "123e4567-e89b-12d3-a456-426614174000",
    "playedAt": "2024-02-07T41:44:28551",
    "won": true,
    "difficulty": "EASY",
    "gridSize": 8,
    "numberOfMines": 10
  }
]
```

## Development Notes

### Local Development Setup

1. Clone the repository

2. Build with Maven:
```bash
mvn clean install
```

3. Run with Docker Compose (recommended):
```bash
docker-compose up --build
```

### Database Access

- The MySQL database is exposed on port 3306
- Default credentials:
    - Username: root
    - Password: root
    - Database: minesweeper
- Connection URL: `jdbc:mysql://localhost:3306/minesweeper`

### Logging

The application includes detailed logging for:
- Spring Security (DEBUG level)
- Hibernate SQL queries (DEBUG level)
- SQL parameter binding (TRACE level)

## Contact

For any questions or issues, please open an issue or contact the maintainer.