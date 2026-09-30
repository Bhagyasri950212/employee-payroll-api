````markdown
# Employee Management & Payroll Backend API

## 1. Project Overview

The Employee Management & Payroll Backend API is a Java-based REST API developed using Spring Boot.

The system provides APIs for:

- Employee management
- Payroll management
- User registration and login
- JWT-based authentication
- Role-based authorization
- Validation and error handling
- MySQL database integration

The project follows a layered architecture:

**Controller → Service → Repository → MySQL**

---

## 2. Technology Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- MySQL 8
- Maven
- Postman
- IntelliJ IDEA

---

## 3. Architecture

```text
Client / Postman
       |
       v
Controller Layer
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
MySQL Database
````

### Main Layers

**Controller**

* Handles HTTP requests and responses.
* Defines REST API endpoints.

**Service**

* Contains business logic.
* Performs validation and database-related operations.

**Repository**

* Uses Spring Data JPA to communicate with the database.

**Entity**

* Represents database tables.

**Security**

* Handles JWT authentication and role-based authorization.

---

## 4. Database Design

The application uses MySQL database:

```text
employee_payroll_db
```

Main tables:

* users
* employees
* payroll

### Relationships

```text
EMPLOYEES 1 -------- * PAYROLL
```

One employee can have multiple payroll records.

---

## 5. Authentication

The application uses JWT-based authentication.

### Register

```http
POST /api/auth/register
```

Example request:

```json
{
  "username": "employee1",
  "password": "password123"
}
```

Newly registered users are assigned the `EMPLOYEE` role by default.

### Login

```http
POST /api/auth/login
```

The login API returns a JWT token.

The token is sent in subsequent requests using:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## 6. Role-Based Authorization

The application supports two roles:

* ADMIN
* EMPLOYEE

### ADMIN

Admin users can:

* Create employees
* Update employees
* Update employee status
* Create payroll records
* View employees
* View payroll records

### EMPLOYEE

Employee users can:

* View employee information
* View payroll information

Employees cannot create or modify employee or payroll records.

---

## 7. Employee APIs

### Create Employee

```http
POST /api/employees
```

Access:

```text
ADMIN
```

### Get Employee

```http
GET /api/employees/{id}
```

Access:

```text
ADMIN / EMPLOYEE
```

### Get All Employees

```http
GET /api/employees
```

Access:

```text
ADMIN / EMPLOYEE
```

### Filter Employees by Department

```http
GET /api/employees?department=IT
```

### Filter Employees by Status

```http
GET /api/employees?status=ACTIVE
```

### Update Employee

```http
PUT /api/employees/{id}
```

Access:

```text
ADMIN
```

### Update Employee Status

```http
PATCH /api/employees/{id}/status?status=INACTIVE
```

Access:

```text
ADMIN
```

---

## 8. Payroll APIs

### Create Payroll

```http
POST /api/payroll/employee/{employeeId}
```

Access:

```text
ADMIN
```

### Get Payroll by ID

```http
GET /api/payroll/{id}
```

Access:

```text
ADMIN / EMPLOYEE
```

### Get Payroll by Employee

```http
GET /api/payroll/employee/{employeeId}
```

Access:

```text
ADMIN / EMPLOYEE
```

### Get All Payroll Records

```http
GET /api/payroll
```

Access:

```text
ADMIN / EMPLOYEE
```

---

## 9. Payroll Calculation

The net salary is calculated automatically.

```text
Net Salary = Basic Salary + Allowances - Deductions
```

Example:

```text
Basic Salary = 50,000
Allowances   = 5,000
Deductions   = 2,000

Net Salary = 50,000 + 5,000 - 2,000
           = 53,000
```

The client does not directly provide the net salary.

---

## 10. Validation

Bean Validation is used for request validation.

Examples:

* Name cannot be empty.
* Email must be valid.
* Required fields cannot be empty.
* Salary values cannot be negative.
* Username and password are required.

Invalid requests return:

```text
400 Bad Request
```

---

## 11. Error Handling

A global exception handler is implemented using:

```text
@RestControllerAdvice
```

The application handles:

* Employee not found
* Payroll not found
* Invalid request data
* Duplicate username
* Duplicate employee email

Example:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Employee not found with id: 999"
}
```

---

## 12. Running the Application

### Prerequisites

Install:

* Java 17
* Maven
* MySQL 8
* IntelliJ IDEA

### Database

Create the database:

```sql
CREATE DATABASE employee_payroll_db;
```

Update the database username and password in:

```text
src/main/resources/application.properties
```

### Start the Application

Run:

```text
EmployeePayrollApiApplication
```

The application starts on:

```text
http://localhost:8080
```

---

## 13. API Testing

The APIs were tested using Postman.

Tested scenarios include:

* User registration
* User login
* JWT authentication
* Employee creation
* Employee retrieval
* Employee update
* Employee status update
* Payroll creation
* Payroll retrieval
* Role-based authorization
* Validation errors
* Not-found errors

Examples of tested responses:

```text
ADMIN creating employee → 201 Created
EMPLOYEE creating employee → 403 Forbidden
Getting existing employee → 200 OK
Getting non-existing employee → 404 Not Found
Invalid employee request → 400 Bad Request
Payroll creation by ADMIN → 201 Created
```

---

## 14. Project Structure

```text
src/main/java/com/example/employeepayroll
│
├── controller
│   ├── AuthController
│   ├── EmployeeController
│   └── PayrollController
│
├── dto
│   ├── LoginRequest
│   └── RegisterRequest
│
├── entity
│   ├── Employee
│   ├── EmploymentStatus
│   ├── Payroll
│   ├── Role
│   └── User
│
├── exception
│   └── GlobalExceptionHandler
│
├── repository
│   ├── EmployeeRepository
│   ├── PayrollRepository
│   └── UserRepository
│
├── security
│   ├── JwtAuthenticationFilter
│   ├── JwtService
│   └── SecurityConfig
│
└── service
    ├── AuthService
    ├── EmployeeService
    └── PayrollService
```

---

## 15. Technical Decisions

### Spring Boot

Used to build the REST backend and simplify application configuration.

### Spring Data JPA

Used to interact with MySQL without writing SQL for basic CRUD operations.

### JWT

Used for stateless authentication.

### Role-Based Authorization

Used to restrict sensitive operations such as employee creation and payroll creation to administrators.

### MySQL

Used as the relational database for storing users, employees and payroll records.

---

## 16. Assumptions

* Newly registered users receive the EMPLOYEE role by default.
* ADMIN access is managed separately.
* Payroll is associated with an existing employee.
* Net salary is calculated by the backend.
* Payroll records contain a pay month.
* Employee and payroll data are stored in MySQL.

---

## 17. Future Improvements

Possible future improvements include:

* Pagination and sorting
* Payroll update and delete operations
* More detailed audit logging
* Refresh tokens
* Improved exception types and HTTP status handling
* API documentation using OpenAPI/Swagger
* Docker support
* Automated unit and integration tests

```

