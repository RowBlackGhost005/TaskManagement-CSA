
# Task Management System

A small Restful API for Task Management that allows you to manage Tasks of multiple Users.

CHECK THE VIDEO DEMO: https://youtu.be/IDlau5GxE_c

## Technologies
- Java 21
- Spring Boot 3.5
- MySQL

## Features
- CRUD Tasks.
- Authentication with JWT.
- Role based authorization.
- Admin only endpoints for management.
- Logging API requests and errors into a file.
- Unitary and Integration test for verification.
- Single monolithic app divided in logical packages.

# HOW TO RUN

**1. Clone the repo**

You can use GIT or download it as a ZIP in the *`<>Code`* button
```
git clone https://github.com/RowBlackGhost005/TaskManagement-CSA.git
```

---

**2. Setup your enviroment variables**

Create the JWT Secret Key for signing tokens, you can declare it in two ways:

*Note: Your JWT secret must be at least 24 characters long.*

*(Option 1)* - If you want to use Env variables open your CMD [Windows] and type:
```
set USERSERVICE_JWT_SECRET=YourSecretCustomJWTKey
```

*(Option 2)* - Edit the application properties file directly, go to `/src/main/resources/application.properties` and go to the Secret Key section to replace the env reference `${USERSERVICE_JWT_SECRET}`:
```
### JWT SECRET KEY
jwt.secret=ThisIsAHardcodedJwtSecretKey
```

---

**3. Setup the Databases in MySQL**

Open MySQL Workbench or CLI to create the databases (Tables are automatically generated)
```
CREATE DATABASE TaskManagementCSA;
```
*Note: You *can* name your own databases name but you will be required to change the reference in the application.properties file*

---

**4. Execute the app**

Once everything is set up you can execute the `MAIN` class located at `/src/main/java/com.marin.TaskManagement.TaskManagementApplication.java`.

Wait for the API to go up and once is done you it will be deployed in the 8080 port
```
http://localhost:8080
```

---

# API Reference

Now that you have the Task Management up and running you will be able to throw HTTP request at it, below you can see the different endpoints available.

You can also check the documentation in the Swagger UI in the following endpoint:
http://localhost:8080/swagger-ui/index.html

### Auth

#### Register

```http
POST http://localhost:8080/api/auth/register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Credentials` | `JSON` | {"username":"John" , "password":"mypassword"} | 

Response: (String)
```
User registered sucessfully
```

---

#### Login

```http
POST http://localhost:8080/api/auth/login
```

| Parameter | Type     | Description                | Response                |
| :-------- | :------- | :------------------------- | :------------------------- |
| `Credentials` | `JSON` | {"username":"John" , "password":"mypassword"} | JSON|

Response: Authentication Response (JWT Token)
```JSON
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG61piIsImlkDjoxLCJyb2xlcyI6WyJST0xFX0FETDlOIl0sImlhdCI6MTc0ODcyMTMwMSwiZXhwIjoxNzQ4ODA3NzAxfQ.by0Xhs33hnCXJw_CMQdR5O13GDw4itEfJ6igF3UK5c0",
    "tokenType": "Bearer"
}
```

---

### Tasks

#### Create Task

```http
POST http://localhost:8080/api/tasks
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 
| `Task` | `JSON` | Task to create |

Request Body:
```JSON
{
    "title" : "Do Homework",
    "description": "CSA Homework",
    "status" : "PENDING",
    "priority" : "LOW",
    "dueDate": "2025-06-01T20:30:00"
}
```

Response: Task created (JSON Object)
```JSON
{
    "id" : 4
    "title" : "Do Homework",
    "description": "CSA Homework",
    "status" : "PENDING",
    "priority" : "LOW",
    "dueDate": "2025-06-01T20:30:00"
}
```

---

#### Fetch User Tasks

Returns the current authenticated user (Defined by the JWT) tasks.
```http
GET http://localhost:8080/api/tasks
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 

Response: Task created by the user (JSON Array)
```JSON
[
    {
        "id": 1,
        "title": "Do Homework",
        "description": "CSA Homework",
        "status": "PENDING",
        "priority": "LOW",
        "dueDate": "2025-05-28T20:30:00"
    },
    {
        "id": 4,
        "title": "Test Task",
        "description": "Description of a Task",
        "status": "PENDING",
        "priority": "LOW",
        "dueDate": "2025-06-01T20:30:00"
    }
]
```

---

#### Update Task

Updates only the data sent if there is an ID match
```http
PUT http://localhost:8080/api/tasks/{taskId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 
| `{taskId}` | `INT` | ID of the TASK to update |

Request Body:
```JSON
{
    "status" : "COMPLETED"
}
```

Response: Task created (JSON Object)
```JSON
{
    "id" : 4
    "title" : "Do Homework",
    "description": "CSA Homework",
    "status" : "COMPLETED",
    "priority" : "LOW",
    "dueDate": "2025-06-01T20:30:00"
}
```

---

#### Delete Task

Deletes the task that matches the given ID only if the current authenticated user owns the Task.
```http
PUT http://localhost:8080/api/tasks/{taskId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 
| `{taskId}` | `INT` | ID of the TASK to delete |


Response: Message (Text)
```
Task deleted successfully
```

---

### ADMIN ONLY endpoints
There are several endpoints only for admins, this repo provides only one Admin pre-persisted at first run that is used for generating the ADMIN Token to access this endpoints.

#### Fetch All Tasks

Shows all tasks alongside its owner User
```http
GET http://localhost:8080/api/tasks/admin/list
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 


Response: All tasks registered (JSON Array)
```JSON
[
    {
        "id": 2,
        "user": {
            "id": 2,
            "username": "User"
        },
        "title": "Read book",
        "description": "Continue reading",
        "status": "PENDING",
        "priority": "LOW",
        "dueDate": "2025-05-30T15:30:00"
    },
    {
        "id": 3,
        "user": {
            "id": 1,
            "username": "admin"
        },
        "title": "Do Homework",
        "description": "CSA Homework",
        "status": "PENDING",
        "priority": "LOW",
        "dueDate": "2025-05-28T20:30:00"
    },
    {
        "id": 4,
        "user": {
            "id": 1,
            "username": "admin"
        },
        "title": "Test Task",
        "description": "Description of a Task",
        "status": "PENDING",
        "priority": "LOW",
        "dueDate": "2025-06-01T20:30:00"
    }
]
```

---

#### Fetch task by Id

Attemps to fetch a Task that has the given ID
```http
GET http://localhost:8080/api/tasks/admin/task/{taskId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 
| `{taskId}` | `INT` | ID of the TASK to fetch |


Response: Task (JSON Object)
```JSON
{
    "id": 1,
    "user": {
        "id": 1,
        "username": "admin"
    },
    "title": "Do project planning",
    "description": "For project #6",
    "status": "PENDING",
    "priority": "LOW",
    "dueDate": "2025-06-02T12:45:00"
}
```

---

#### Delete task

Deletes ANY task with matching ID no matter ownership.
```http
DELETE http://localhost:8080/api/tasks/admin/task/{taskId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 
| `{taskId}` | `INT` | ID of the TASK to delete |


Response: Message (Text)
```
Task deleted successfully
```

---

#### Users task count

Returns all Users with the task count they own.
```http
GET http://localhost:8080/api/users/admin/taskCount
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- | 
| `Authorization` | `HEADER` | Bearer {JWT TOKEN} | 


Response: Users with Task Count (JSON Array)
```JSON
[
    {
        "id": 1,
        "username": "admin",
        "taskCount": 4
    },
    {
        "id": 2,
        "username": "Row",
        "taskCount": 1
    }
]
```
---

# Dependencies
This project uses some dependencies to work properly and are listed below:

### Actuator
Provides endpoints for monitoring the app.

### JJWT
Provides functionallity for JWT creationg and parsing.

### Open API
Provides the annotations for documentation.

### Prometheus
Provides a endpoint for better management and aggregation of monitoring data.

### H2 Database
Used for Unitary and Integration tests.

### JPA
Used for defining entities and interacting with the databases

### MySQL Connector
Used for establishing connection with MySQL.

### Spring Boot
Used Security / Web / Test / Actuator framework for securing the app, allowing the creation of the RESTful API, creation of Unit and Integration test and provide metrics of the API.

# Credits
```
Developed by: Luis Marin
Project part of CSA.
```
