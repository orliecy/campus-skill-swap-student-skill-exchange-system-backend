# Campus SkillSwap Student Skill Exchange System

**Student Name:** Mazimpaka Ikirezi Ora Lesly 
**Student ID:** 27802  
**Course:** INSY 8322 - Web Technology and Internet
**Project:** Campus SkillSwap Student Skill Exchange System

## Project Overview

Campus SkillSwap is a student skill exchange management system developed using Spring Boot and PostgreSQL.

The main purpose of this project is to help university students exchange skills and learn from one another. For example, a student who knows graphic design can offer that skill and request help with Java programming from another student.

The system manages learners, mentors, skills, study spots, exchange requests, and exchange reviews.

This project was developed as part of my Web Technology midterm examination.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Postman
- Git and GitHub
- Visual Studio Code

## Main Features

### Part A — Basic CRUD Operations

I implemented basic CRUD operations for the main entities in the system.

These operations allow users to create, view, update, and delete records.

The main entities are:

- Learner
- Mentor
- Skill
- StudySpot
- ExchangeRequest

### Part B — Derived Queries

I used Spring Data JPA derived query methods to retrieve information from the database based on different conditions.

### Part C — JPQL Queries

I implemented JPQL queries to retrieve specific records and handle more advanced database searches.

### Part D — Business Rules

I added business rules to make sure the system works correctly.

These include:

- Preventing exchange requests from being created with past dates.
- Preventing a mentor from being booked more than once on the same date.
- Controlling how exchange request statuses can change.
- Assigning skills to mentors.
- Changing a mentor's study spot.

### Part E — Exchange Reviews

I implemented exchange reviews so that completed skill exchange sessions can be reviewed.

The system only allows reviews for completed exchanges. It also prevents deleting an exchange request that still has reviews.

### Part F — Advanced Queries

I implemented advanced database operations, including:

- Counting exchange requests according to their status.
- Cancelling exchange requests for a particular mentor on a selected date.

### Bonus — Dashboard and Pagination

I implemented two additional features.

**Dashboard Summary**

The dashboard provides information about:

- Total learners
- Total mentors
- Available study spots
- Exchange sessions scheduled for today
- Requested exchanges
- Total reviews

**Pagination and Sorting**

I added pagination to display exchange requests in smaller groups instead of returning all records at once.

The results can also be sorted by session date.

## Database Configuration

The project uses PostgreSQL.

Database name:

`campus_skillswap_db`

The database connection is configured in:

`src/main/resources/application.properties`

Example configuration:

```properties
spring.application.name=campus-skill-swap-student-skill-exchange-system
spring.datasource.url=jdbc:postgresql://localhost:5432/campus_skillswap_db
spring.datasource.username=postgres
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8083
```

The PostgreSQL username and password should be changed if the local database uses different credentials.

## How to Run the Project

1. Clone the GitHub repository.
2. Open the project folder in Visual Studio Code.
3. Make sure Java, Maven, and PostgreSQL are available.
4. Create a PostgreSQL database named `campus_skillswap_db`.
5. Check the database connection in `application.properties`.
6. Open the VS Code terminal in the project folder.
7. Run the application using:

```cmd
.\mvnw.cmd clean spring-boot:run
```

8. Wait until Spring Boot starts successfully.

The backend runs at:

`http://localhost:8083`

## API Testing

I used Postman to test the API endpoints after implementing each part of the project.

Some examples are:

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/exchange-requests/all` | View all exchange requests |
| GET | `/api/exchange-requests/by-status?status=REQUESTED` | Find requests by status |
| GET | `/api/exchange-requests/stats/by-status` | Count requests by status |
| PATCH | `/api/exchange-requests/cancel-day?mentorId={id}&date=2026-12-15` | Cancel requests for a mentor on a selected date |
| GET | `/api/dashboard/summary` | View dashboard statistics |
| GET | `/api/exchange-requests/page?page=0&size=5&sort=sessionDate,desc` | View paginated and sorted requests |

The API uses JSON for request and response data.

## How I Worked on the Project

I developed the project step by step, starting with the entities and repositories before implementing the services and controllers.

After completing each section, I compiled and ran the application, then tested the endpoints using Postman.

I also used Git to commit my changes and pushed the completed sections to GitHub.

## Challenges I Faced

While developing the project, I experienced some challenges with database configuration, Java files, and application errors.

I worked through these issues by checking the terminal output, correcting the affected files, compiling the project again, and testing the endpoints.

This helped me understand how the different parts of a Spring Boot application work together.

## What I Learned

Through this project, I improved my understanding of:

- Creating REST APIs with Spring Boot
- Connecting Java applications to PostgreSQL
- Working with entities and relationships
- Using Spring Data JPA repositories
- Writing derived queries and JPQL queries
- Implementing business rules in service classes
- Testing APIs using Postman
- Using Git and GitHub to manage project changes

## Conclusion

Campus SkillSwap is a backend application that supports student skill exchange activities.

By completing this project, I gained practical experience in developing a Spring Boot application, managing database records, implementing business rules, and testing REST API endpoints.