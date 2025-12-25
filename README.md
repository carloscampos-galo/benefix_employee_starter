# Employee Starter API

A Spring Boot RESTful API for employee management with PostgreSQL database and Docker support.

## Tech Stack

- Java 21
- Spring Boot 3.4.1
- Spring Data JPA
- PostgreSQL 17
- Docker & Docker Compose
- Maven

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose (for containerized setup)

### Run with Docker

```bash
docker-compose up --build
```

This starts both PostgreSQL and the application. The API will be available at `http://localhost:8080`.

### Run Locally

1. Start PostgreSQL (localhost:5432) with database `employeedb`
2. Run the application:

```bash
mvn spring-boot:run
```

### Configuration

Environment variables (with defaults):

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_HOST` | localhost | Database host |
| `DB_PORT` | 5432 | Database port |
| `DB_NAME` | employeedb | Database name |
| `DB_USERNAME` | postgres | Database username |
| `DB_PASSWORD` | postgres | Database password |
| `SERVER_PORT` | 8080 | Application port |

## Docker Commands

```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Build

```bash
# Build JAR
mvn clean package

# Build Docker image
docker build -t employee-starter .
```
