# Task Management API

A simple Task Management REST API built using Spring Boot following:

- Domain-Driven Design (DDD)
- Test-Driven Development (TDD)
- In-Memory Repository Pattern
- Layered Architecture

This project demonstrates clean architecture principles without using a database.

---

# Features

- Create Tasks
- Retrieve Task by ID
- Retrieve All Tasks
- Update Existing Tasks
- Delete Tasks
- Global Exception Handling
- In-Memory Data Storage
- Due Date Sorting
- Unit Tests using JUnit 5 and Mockito
- Controller Tests using MockMvc

---

# Tech Stack

- Java 17+
- Spring Boot
- Maven
- JUnit 5
- Mockito
- MockMvc

---

# Architecture

The project follows a lightweight DDD structure.

```text
taskmanagement/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/satcom/taskmanagement/
│   │   │       ├── TaskmanagementApplication.java
│   │   │       │
│   │   │       ├── application/
│   │   │       │   ├── service/
│   │   │       │   │   └── TaskService.java
│   │   │       │   ├── dto/
│   │   │       │   │   └── ErrorResponse.java
│   │   │       │   └── exception/
│   │   │       │       ├── GlobalExceptionHandler.java
│   │   │       │       └── TaskNotFoundException.java
│   │   │       │
│   │   │       ├── domain/
│   │   │       │   ├── model/
│   │   │       │   │   ├── Task.java
│   │   │       │   │   └── Status.java
│   │   │       │   └── repository/
│   │   │       │       └── TaskRepository.java
│   │   │       │
│   │   │       ├── infrastructure/
│   │   │       │   └── repository/
│   │   │       │       └── InMemoryTaskRepository.java
│   │   │       │
│   │   │       └── presentation/
│   │   │           └── controller/
│   │   │               └── TaskController.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   │
│   └── test/
│       └── java/
│           └── com/satcom/taskmanagement/
│
├── pom.xml
└── README.md

```

---

# Domain Model

## Task

Task entity contains:

- id
- title
- description
- status
- dueDate

The `id` is auto-generated using:

```java
UUID.randomUUID().toString()
```

The default task status is:

```text
PENDING
```

---

# Task Status

```java
public enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}
```

---

# REST API Endpoints

## Create Task

```http
POST /tasks
```

### Request Body

```json
{
  "title": "Learn Spring Boot",
  "description": "Practice REST APIs",
  "dueDate": "2026-06-01"
}
```

### Response

```json
{
  "id": "generated-id",
  "title": "Learn Spring Boot",
  "description": "Practice REST APIs",
  "status": "PENDING",
  "dueDate": "2026-06-01"
}
```

---

## Get Task By ID

```http
GET /tasks/{id}
```

---

## Get All Tasks

```http
GET /tasks
```

Tasks are sorted by `dueDate` in ascending order.

---

## Update Task

```http
PUT /tasks/{id}
```

---

## Delete Task

```http
DELETE /tasks/{id}
```

Returns:

```http
204 No Content
```

---

# Global Exception Handling

The project uses `@RestControllerAdvice` for centralized exception handling.

Example error response:

```json
{
  "message": "Task not found with id: abc123",
  "status": 404,
  "timestamp": "2026-05-27T12:00:00"
}
```

---

# Testing

The project follows a Test-Driven Development (TDD) approach.

## Service Layer Tests

Uses:
- JUnit 5
- Mockito

Test coverage includes:
- Task creation
- Get task by ID
- Get all tasks
- Delete task
- Exception scenarios

---

## Controller Layer Tests

Uses:
- `@WebMvcTest`
- `MockMvc`
- `@MockBean`

Controller tests validate:
- HTTP status codes
- JSON responses
- Exception handling
- REST endpoint behavior

---

# Prerequisites & Installation

## System Requirements

- **Java**: 17 or higher
- **Maven**: 3.6.0 or higher
- **Git**: Latest version
- **OS**: Windows, macOS, or Linux

---

## Step 1: Install Java 17+

### Windows

1. Download Java from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) or use [OpenJDK](https://jdk.java.net/)
2. Run the installer and follow the installation wizard
3. Set `JAVA_HOME` environment variable:
   - Right-click **This PC** → **Properties** → **Advanced system settings**
   - Click **Environment Variables**
   - Add new **System Variable**: `JAVA_HOME = C:\Program Files\Java\jdk-17` (adjust path as needed)

### macOS

```bash
# Using Homebrew
brew install openjdk@17

# Set JAVA_HOME in ~/.zshrc or ~/.bash_profile
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Verify Java Installation

```bash
java -version
```

Should output something like:
```
java version "17.x.x"
```

---

## Step 2: Install Maven

### Windows

1. Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
2. Extract to a folder (e.g., `C:\maven`)
3. Add Maven to PATH:
   - Right-click **This PC** → **Properties** → **Advanced system settings**
   - Click **Environment Variables**
   - Edit **Path** variable and add `C:\maven\bin`

### macOS

```bash
# Using Homebrew
brew install maven
```

### Linux (Ubuntu/Debian)

```bash
sudo apt install maven
```

### Verify Maven Installation

```bash
mvn -version
```

Should output:
```
Apache Maven 3.6.x or higher
Java version: 17.x.x
```

---

## Step 3: Install Project Dependencies

### Clone Repository

```bash
git clone <repository-url>
cd taskmanagement
```

### Download & Install Dependencies

Maven automatically downloads dependencies when you build the project:

```bash
mvn clean install
```

This command:
- **Cleans** previous builds
- **Validates** the project
- **Downloads** all dependencies from pom.xml
- **Compiles** source code
- **Runs** tests
- **Packages** the application

---

## Step 4: Run the Application

### Option 1: Using Spring Boot Maven Plugin

```bash
mvn spring-boot:run
```

### Option 2: Run JAR File

```bash
mvn clean package
java -jar target/taskmanagement-0.0.1-SNAPSHOT.jar
```

### Verify Application is Running

Application starts on:

```text
http://localhost:8080
```

Open your browser or use curl to verify:

```bash
curl http://localhost:8080/tasks
```

---

## Troubleshooting

### Error: "java: command not found"
- Java is not installed or JAVA_HOME is not set correctly
- Verify with: `java -version`
- Set JAVA_HOME following the installation steps above

### Error: "mvn: command not found"
- Maven is not installed or not in PATH
- Verify with: `mvn -version`
- Restart terminal after adding Maven to PATH

### Error: "Port 8080 already in use"
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```
  
---

# Running the Application

The application will start with all dependencies automatically installed by Maven.

---

# Example Curl Commands

## Create Task

```bash
curl -X POST http://localhost:8080/tasks \
-H "Content-Type: application/json" \
-d '{
  "title":"Learn DDD",
  "description":"Study architecture",
  "dueDate":"2026-06-10"
}'
```

---

## Get All Tasks

```bash
curl http://localhost:8080/tasks
```
