# Activity Log API

A simple Spring Boot + Kotlin API for students to log their activities.

## Features

- User registration and login  
- Create, view, update, and delete activity logs  
- Filter logs by **type** and **date**  

---

## Tech Stack

- Kotlin  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- Flyway (for database migrations)  

---

## Database Setup

1. Install PostgreSQL.  
2. Create a database:

```sql
CREATE DATABASE activity_log;
```

3. Configure application.properties (or application.yml) with your database connection:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/activity_log
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

4. Flyway will automatically run migrations on startup. Ensure your migration scripts are under src/main/resources/db/migration.

---

## Running the Project

## Using IntelliJ / IDE

1. Open the project.
2. Ensure JDK 17+ is configured.
3. Run the main application class ActivityLogApiApplication.kt.

## Using Command Line

1. Build the project:

```bash
./gradlew build
```

2. Run the application

```bash
./gradlew bootRun
```

The API will start on http://localhost:8080

---

## API Endpoints

## Users
- POST /users/register - Register a new user
- POST /users/login - Login with username and password
- GET /users/{id} - Get user by ID

## Activity Types
- GET /activity-types - Get all activity types
- GET /activity-types/{id} - Get activity type by ID


## Activity Logs
- GET /logs - Get all logs
- GET /logs/{id} - Get log by ID
- GET /logs/user/{id} - Get logs by user (Optional query params for filter: activityTypeId, startDate, endDate)
- POST /logs - Create new activity log
- PUT /logs/{id} - Update a log description and/or type
- DELETE /logs/{id} - Delete a log by ID

---

## Example Request

Create a log

```bash
    POST http://localhost:8080/logs
    Content-Type: application/json

{
    "userId": 1
    "activityTypeId": 1
    "description": "Sample activity description"
}
```

Filter logs by user, type, and date

```bash
    GET http://localhost:8080/logs/user/1?activityTypeId=2&startDate=2025-12-01T00:00:00&endDate=2025-12-10T23:59:59
```

---

## Notes

- Passwords are stored in plain text for simplicity
- DTO validations use @NotNull and @NotBlank
- Logs can be activity type, date range, and user
