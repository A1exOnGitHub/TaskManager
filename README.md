# Task Manager

A desktop task management application built with Java and JavaFX. The application supports creating, editing, completing, deleting, searching, filtering, and sorting tasks while storing task data persistently in PostgreSQL.

## Features

* Create tasks with a title, priority, and due date
* Edit existing tasks
* Mark tasks as completed
* Delete tasks
* Search tasks by title
* Filter tasks by priority and status
* Sort tasks by title, due date, priority, or status
* Automatically determine task status:

    * Upcoming
    * Due Today
    * Overdue
    * Completed
* Persist tasks between application sessions using PostgreSQL
* Display database errors to the user
* Unit testing with JUnit

## Technologies

* Java 25
* JavaFX 25
* PostgreSQL
* JDBC
* Maven
* JUnit 5
* Git / GitHub

## Project Structure

The application separates the user interface, application logic, and persistence layer.

```text
JavaFX UI
    |
    v
TaskManager
    |
    v
TaskRepository
    |
    v
PostgresTaskRepository
    |
    v
PostgreSQL
```

### Main Components

**`Main.java`**
Handles the JavaFX user interface and user interactions.

**`Task.java`**
Represents an individual task and stores information such as title, priority, due date, completion status, and database ID.

**`TaskManager.java`**
Contains task-management logic such as adding, updating, deleting, completing, and searching tasks.

**`TaskRepository.java`**
Defines the repository interface used for task persistence.

**`PostgresTaskRepository.java`**
Implements the repository using JDBC and PostgreSQL.

**`InMemoryTaskRepository.java`**
Provides an in-memory repository implementation used for testing.

**`DatabaseConnection.java`**
Creates JDBC connections using database credentials stored in environment variables.

## Database

The application uses a PostgreSQL `tasks` table with fields for:

* ID
* Title
* Priority
* Due date
* Completion status
* Creation timestamp

Example schema:

```sql
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    due_date DATE NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Environment Variables

The application expects the following environment variables:

```text
TASK_DB_URL=jdbc:postgresql://localhost:5432/task_manager
TASK_DB_USER=postgres
TASK_DB_PASSWORD=your_password
```

Database credentials are not stored directly in the source code.

## Running the Application

1. Install PostgreSQL and create a database named:

```text
task_manager
```

2. Create the `tasks` table using the database schema shown above.

3. Configure the following environment variables before launching the application:

```text
TASK_DB_URL=jdbc:postgresql://localhost:5432/task_manager
TASK_DB_USER=postgres
TASK_DB_PASSWORD=your_password
```

If using IntelliJ IDEA, these variables can be added to the Maven run configuration for `javafx:run`.

4. Clone the repository and open the project.

5. Run the application using:

```bash
mvn javafx:run
```

The application requires the database environment variables to be available to the Maven process before it starts.


## Testing

The project uses JUnit 5 for testing.

Tests cover areas including:

* Task status calculation
* Task updates
* TaskManager operations
* Searching and filtering
* In-memory repository behavior

Run the test suite with:

```bash
mvn test
```

## Future Improvements

* PostgreSQL integration tests
* Improved database connection error handling
* Additional UI styling
* More advanced filtering and sorting options

## Author

Alexander Zelayandia
