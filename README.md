# Unicircle

Unicircle, also named `GroupUp` in the Spring Boot application metadata, is a backend API for creating and joining short-term activity groups. It is designed for college users with `@learner.manipal.edu` email addresses and supports group discovery, tags, membership requests, profile management, and JWT-based authentication.

Frontend Rpository: https://github.com/KaranLalwani-dev/Unicircle-frontend-v2

## Part 1: Software Details

### Architecture

The project follows a layered Spring Boot architecture:

```text
Client / Frontend
      |
      v
Spring MVC Controllers
      |
      v
Service Interfaces and Implementations
      |
      v
Spring Data JPA Repositories
      |
      v
PostgreSQL Database
```

Main package:

```text
com.teamdev.group_up
```

Important folders:

| Folder | Purpose |
| --- | --- |
| `controller` | REST API endpoints for auth, users, groups, tags, requests, and utility values. |
| `service` | Business service contracts. |
| `service/Impl` | Business logic implementations. |
| `repository` | Spring Data JPA repositories. |
| `entity` | Database entities and join table models. |
| `dto` | Request and response payloads. |
| `mapper` | MapStruct mappers for entity/DTO conversion. |
| `security` | JWT authentication, Spring Security, CORS, and password encoder configuration. |
| `config` | Startup data seeding. |
| `error` | Global exception handling and API error models. |
| `specification` | Dynamic group search filters. |
| `enums` | Fixed values for branch, year, group status, and request status. |

### Technology Stack

| Area | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.0.2 |
| API | Spring Web MVC REST APIs |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL configured by default |
| Additional DB Driver | Oracle JDBC driver included in dependencies |
| Security | Spring Security, stateless JWT authentication |
| Password Hashing | BCrypt |
| Validation | Jakarta Bean Validation |
| Mapping | MapStruct |
| Boilerplate Reduction | Lombok |
| Build Tool | Maven Wrapper |
| Containerization | Docker multi-stage build |
| Monitoring Base | Spring Boot Actuator dependency |

### Core Features

- User signup and login with JWT authentication.
- College email validation for `@learner.manipal.edu` accounts.
- User profile read and update APIs.
- Group creation with title, description, date/time, maximum members, and tags.
- Group browsing with filters for keyword, tags, activity date range, creator year, and creator branch.
- Group detail view, own groups, joined groups, members list, leave group, and cancel group.
- Join request workflow with pending, accepted, and rejected statuses.
- Tag listing with startup seeding for default activity categories.
- Utility endpoints for frontend dropdown values.
- Centralized exception handling.

### Database Schema

The application uses JPA entities and Hibernate schema generation. Current configuration uses:

```yaml
spring.jpa.hibernate.ddl-auto: update
```

Main tables:

| Table | Entity | Purpose |
| --- | --- | --- |
| `users` | `User` | Stores registered users and login credentials. |
| `groups` | `Group` | Stores activity groups created by users. |
| `tags` | `Tag` | Stores group categories such as Cab Share, Study Group, Sports, etc. |
| `group_tags` | `GroupTag` | Join table between groups and tags. |
| `group_members` | `GroupMember` | Join table between groups and accepted members. |
| `join_requests` | `JoinRequest` | Stores requests from users who want to join a group. |

#### `users`

| Field | Notes |
| --- | --- |
| `userId` | Primary key. |
| `name` | Required, up to 50 characters. |
| `username` | Required, unique college email address. |
| `password` | Required hashed password. |
| `year` | Required enum: `FIRST_YEAR`, `SECOND_YEAR`, `THIRD_YEAR`, `FOURTH_YEAR`. |
| `branch` | Required enum: `CSE`, `AI`, `DS`, `CYS`, `IT`, `ECE`, `ME`, `CE`, `EE`. |
| `instagramId` | Optional. |
| `phoneNumber` | Optional. |
| `createdAt` | Created timestamp. |
| `updatedAt` | Updated timestamp. |

#### `groups`

| Field | Notes |
| --- | --- |
| `groupId` | Primary key. |
| `creatorId` | Foreign key to `users`. |
| `title` | Required, up to 200 characters. |
| `description` | Required, up to 4000 characters in the entity. |
| `activityDateTime` | Required future activity date/time. |
| `maxMembers` | Required, expected range 2 to 20 from request validation. |
| `status` | Enum: `OPEN`, `FULL`, `COMPLETED`, `CANCELLED`. Defaults to `OPEN`. |
| `createdAt` | Created timestamp. |

#### `tags`

| Field | Notes |
| --- | --- |
| `tagId` | Primary key. |
| `tagName` | Required and unique. |

Default seeded tags:

- Cab Share
- Study Group
- Hackathon
- Hangout
- Sports
- Travel/Trip
- Food/Restaurant
- Project Collaboration
- Event/Workshop
- Gaming

#### `group_tags`

Composite key:

| Field | Notes |
| --- | --- |
| `group_id` | Foreign key to `groups`. |
| `tag_id` | Foreign key to `tags`. |

#### `group_members`

Composite key:

| Field | Notes |
| --- | --- |
| `group_id` | Foreign key to `groups`. |
| `user_id` | Foreign key to `users`. |
| `joinedAt` | Timestamp when the user joined the group. |

#### `join_requests`

| Field | Notes |
| --- | --- |
| `requestId` | Primary key. |
| `groupId` | Foreign key to `groups`. |
| `userId` | Foreign key to `users`. |
| `status` | Enum: `PENDING`, `ACCEPTED`, `REJECTED`. Defaults to `PENDING`. |
| `requestedAt` | Timestamp when the request was created. |
| `respondedAt` | Timestamp when the request was accepted or rejected. |

### API Endpoints

Base API path:

```text
/api
```

#### Authentication

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `POST` | `/api/auth/signup` | Register a new user. | No |
| `POST` | `/api/auth/login` | Login and receive an auth response/JWT. | No |

#### Users

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `GET` | `/api/users/me` | Get current user profile. | Yes |
| `PUT` | `/api/users/me` | Update current user profile. | Yes |

#### Groups

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `POST` | `/api/groups` | Create a group. | Yes |
| `GET` | `/api/groups` | Browse/search groups with pagination and filters. | Yes |
| `GET` | `/api/groups/{groupId}` | Get group details. | Yes |
| `GET` | `/api/groups/my-groups` | Get groups created by the current user. | Yes |
| `GET` | `/api/groups/joined` | Get groups joined by the current user. | Yes |
| `PATCH` | `/api/groups/{groupId}/cancel` | Cancel a group. | Yes |
| `POST` | `/api/groups/{groupId}/join` | Request to join a group. | Yes |
| `GET` | `/api/groups/{groupId}/members` | Get members of a group. | Yes |
| `DELETE` | `/api/groups/{groupId}/leave` | Leave a group. | Yes |

Group browse query parameters:

| Parameter | Description |
| --- | --- |
| `keyword` | Search text. |
| `tagIds` | List of tag IDs. |
| `dateFrom` | ISO date/time lower bound. |
| `dateTo` | ISO date/time upper bound. |
| `creatorYear` | Creator year enum. |
| `creatorBranch` | Creator branch enum. |
| `page` | Page number, defaults to `0`. |
| `size` | Page size, defaults to `20`. |

#### Join Requests

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `GET` | `/api/requests/my-requests` | Get requests made by the current user. | Yes |
| `GET` | `/api/requests/for-my-groups` | Get requests for groups owned by the current user. | Yes |
| `POST` | `/api/requests/{requestId}/accept` | Accept a join request. | Yes |
| `POST` | `/api/requests/{requestId}/reject` | Reject a join request. | Yes |

Request query parameters:

| Parameter | Description |
| --- | --- |
| `status` | Optional request status: `PENDING`, `ACCEPTED`, or `REJECTED`. |
| `page` | Page number, defaults to `0`. |
| `size` | Page size, defaults to `20`. |

#### Tags

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `GET` | `/api/tags` | List all tags. | Yes |

#### Utilities

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `GET` | `/api/utils/years` | List year values. | Yes |
| `GET` | `/api/utils/group-statuses` | List group status values. | Yes |
| `GET` | `/api/utils/request-statuses` | List request status values. | Yes |

### Security Model

- Authentication is stateless and uses JWT.
- `/api/auth/**` endpoints are public.
- All other endpoints require authentication.
- Passwords are encoded with BCrypt.
- CSRF is disabled because the API uses stateless token authentication.
- CORS allows the configured frontend origin from `FRONTEND_URL`.
- The backend accepts common HTTP methods: `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, and `OPTIONS`.

### Validation Rules

Signup:

- `username` is required and must be a valid `@learner.manipal.edu` email.
- `password` is required and must be 6 to 50 characters.
- `name`, `year`, and `branch` are required.

Group creation:

- `title` is required and must be 10 to 200 characters.
- `description` is required and must be 20 to 2000 characters.
- `activityDateTime` is required and must be in the future.
- `maxMembers` is required and must be between 2 and 20.
- `tagIds` is required and must contain 1 to 5 tags.

## Part 2: Software Configuration and Deployment

### Prerequisites

- Java 21
- Maven, or use the included Maven Wrapper
- PostgreSQL database
- Docker, optional for container deployment

### Required Environment Variables

The application reads configuration from environment variables in `src/main/resources/application.yaml`.

| Variable | Description |
| --- | --- |
| `DATASOURCE_URL` | JDBC connection string for the PostgreSQL database. |
| `DATASOURCE_USERNAME` | Database username. |
| `DATASOURCE_PASSWORD` | Database password. |
| `FRONTEND_URL` | Allowed frontend origin for CORS. |
| `JWT_SECRET_KEY` | Secret key used to sign and validate JWT tokens. |

Example local configuration:

```bash
export DATASOURCE_URL="jdbc:postgresql://localhost:5432/unicircle"
export DATASOURCE_USERNAME="postgres"
export DATASOURCE_PASSWORD="postgres"
export FRONTEND_URL="http://localhost:3000"
export JWT_SECRET_KEY="replace-with-a-long-secure-secret-key"
```

### Local Database Setup

Create a PostgreSQL database:

```bash
createdb unicircle
```

Hibernate is currently configured with `ddl-auto: update`, so tables are created or updated automatically when the application starts.

For production, consider replacing automatic schema updates with a migration tool such as Flyway or Liquibase.

### Run Locally

From the project root:

```bash
./mvnw spring-boot:run
```

The API runs on the default Spring Boot port:

```text
http://localhost:8080
```

### Build

Create a JAR file:

```bash
./mvnw clean package
```

The generated artifact is created under:

```text
target/
```

### Run Tests

```bash
./mvnw test
```

### Run the Packaged JAR

After building:

```bash
java -jar target/*.jar
```

Make sure all required environment variables are set before running the JAR.

### Docker Build

The included Dockerfile uses a multi-stage build:

1. Maven with Eclipse Temurin 21 builds the application.
2. Eclipse Temurin 21 JRE runs the generated JAR.

Build the image:

```bash
docker build -t unicircle-api .
```

Run the container:

```bash
docker run \
  -p 8080:8080 \
  -e DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/unicircle" \
  -e DATASOURCE_USERNAME="postgres" \
  -e DATASOURCE_PASSWORD="postgres" \
  -e FRONTEND_URL="http://localhost:3000" \
  -e JWT_SECRET_KEY="replace-with-a-long-secure-secret-key" \
  unicircle-api
```

### Deployment Steps

1. Provision a PostgreSQL database.
2. Set all required environment variables in the hosting platform.
3. Build the application with `./mvnw clean package` or build the Docker image.
4. Deploy the JAR or Docker image to the target server/platform.
5. Ensure port `8080` is exposed or mapped through a reverse proxy.
6. Start the application.
7. Verify that authentication and protected API routes work.
8. Check application logs for database connectivity, JWT, CORS, or validation errors.

### Production Configuration Notes

- Use a strong `JWT_SECRET_KEY`.
- Do not commit real secrets to Git.
- Set `spring.jpa.hibernate.ddl-auto` to a safer production value such as `validate` after adding migrations.
- Disable SQL logging in production by setting `show-sql` to `false`.
- Restrict `FRONTEND_URL` to the deployed frontend domain.
- Use HTTPS for both frontend and backend traffic.
- Add centralized logging and monitoring.
- Configure regular database backups.

### Troubleshooting

| Issue | Possible Cause | Fix |
| --- | --- | --- |
| App fails on startup | Missing environment variable | Confirm all required variables are set. |
| Database connection fails | Wrong JDBC URL, username, password, or database not running | Verify PostgreSQL connection details. |
| CORS errors | `FRONTEND_URL` does not match the frontend origin | Update `FRONTEND_URL`. |
| `401 Unauthorized` | Missing or invalid JWT | Login again and send the token with protected requests. |
| Signup rejected | Email is not a `@learner.manipal.edu` address | Use a valid college email. |
| Build fails | Java version mismatch | Use Java 21. |

