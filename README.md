# To-Do CLI

A simple command-line To-Do application built with Spring Boot.

## Features

- Add a task
- List all tasks (with completion status)
- Mark a task as complete
- Swap between in-memory storage and file-based storage via a Spring profile

## Tech Stack

- Java 17
- Spring Boot
- Maven
- JUnit 5 + Mockito (for testing)

## Running the App

### Option 1: Maven

```bash
mvn spring-boot:run
```

Defaults to the `memory` profile. To use file-based storage instead:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=file
```

### Option 2: Build and run the jar

```bash
mvn clean package
java -jar target/to-do-0.0.1-SNAPSHOT.jar
```

To use file-based storage:

```bash
java -jar target/to-do-0.0.1-SNAPSHOT.jar --spring.profiles.active=file
```


### Commands

| Command              | Description                       |
|-----------------------|-----------------------------------|
| `add <description>`  | Adds a new task                   |
| `list`                | Lists all tasks with status       |
| `complete <id>`      | Marks the task with the given id as complete |
| `exit` / `quit`      | Exits the application             |

## Running Tests

```bash
mvn test
```