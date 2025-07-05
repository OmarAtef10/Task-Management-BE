# Task Management Backend

A Spring Boot backend for task management, using PostgreSQL as the database. Dockerized for easy deployment.

## Features

- User registration and login
- Task CRUD operations
- JWT authentication
- RESTful API

## Technologies

- Java 21
- Spring Boot
- PostgreSQL
- Gradle
- Docker & Docker Compose

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21 (for local development)
- Gradle

### Running with Docker

1. Build and start the containers:
   ```sh
   docker-compose up --build
   ```
2. Access the application at `http://localhost:8080`.
3. The PostgreSQL database will be available at `localhost:5432` with the credentials defined in `docker-compose.yml`.
4. The application will be accessible at `http://localhost:8080`

## Postman Collection Link
[API Documentaion](https://www.postman.com/universal-trinity-591560/task-management/collection/1276icq/task-management?action=share&creator=18953959&tab=overview)

# NOTE
- The application uses JWT for authentication. You can register a new user and obtain a token to access protected endpoints.
- Make Sure that this project is running before running the frontend project as it depends that this server is running and on port 8080.

[Front End Repository](https://github.com/OmarAtef10/Task-Management-FE)