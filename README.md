# Benifex Employee Starter API

Benifex Employee Starter API take home technical task.

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

## Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EmployeeServiceImplTest
```

## API Documentation

Swagger UI is available at `http://localhost:8080/swagger-ui.html` when the application is running.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{employeeNo}` | Get employee by employee number |
| POST | `/api/employees` | Create a new employee |
| PUT | `/api/employees/{employeeNo}` | Update an employee |
| DELETE | `/api/employees/{employeeNo}` | Soft delete an employee |

### Sample Request Payload

**Create Employee (POST /api/employees)**

```json
{
  "title": "Mr",
  "firstName": "John",
  "surname": "Doe",
  "dateOfBirth": "1990-01-15",
  "gender": "M",
  "email": "john.doe@example.com",
  "street": "123 Main Street",
  "city": "London",
  "postcode": "SW1A 1AA",
  "country": "UK"
}
```

**Update Employee (PUT /api/employees/{employeeNo})**

```json
{
  "title": "Mr",
  "firstName": "John",
  "surname": "Doe",
  "dateOfBirth": "1990-01-15",
  "gender": "M",
  "email": "john.doe@example.com",
  "street": "456 New Street",
  "city": "Manchester",
  "postcode": "M1 1AA",
  "country": "UK"
}
```

**Sample Response Payload**

```json
{
  "id": 1,
  "employeeNo": "EMP-20250001",
  "title": "Mr",
  "firstName": "John",
  "surname": "Doe",
  "dateOfBirth": "1990-01-15",
  "gender": "M",
  "email": "john.doe@example.com",
  "street": "123 Main Street",
  "city": "London",
  "postcode": "SW1A 1AA",
  "country": "UK",
  "createdAt": "2025-12-26T10:30:00Z",
  "updatedAt": "2025-12-26T10:30:00Z"
}
```

### Error Responses

**404 Not Found - Employee not found**

```json
{
  "status": 404,
  "message": "Employee not found with employeeNo: EMP-2025999",
  "timestamp": "2025-12-26T10:30:00Z",
  "errors": null
}
```

**400 Bad Request - Validation errors**

```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2025-12-26T10:30:00Z",
  "errors": {
    "email": "Invalid email",
    "firstName": "Firstname is required",
    "gender": "Must be one of: M, F"
  }
}
```

**409 Conflict - Email already exists**

```json
{
  "status": 409,
  "message": "Email already exists: john.doe@example.com",
  "timestamp": "2025-12-26T10:30:00Z",
  "errors": null
}
```

## Design Decisions

1. **EmployeeNo Generation**: Uses a database sequence (`employee_no_seq`) to generate unique, sequential employee numbers in the format `EMP-YYYYXXXX`, where YYYY is the current year and XXXX is the sequence number.

2. **Address as Embedded**: Address is stored as embedded fields in the employee table for simplicity, avoiding joins for the common case.

3. **Validation**: Uses Bean Validation (Jakarta Validation) for request validation with custom error messages.

4. **Exception Handling**: Global exception handler provides consistent error responses across all endpoints.

5. **Database Migrations**: Liquibase manages schema changes, ensuring repeatable deployments.

## Needs improvement
The Employee No generator has issue on the format when sequence exceeds 9999. If time permits, one way to solve is to reset the sequence every year.  