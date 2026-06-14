# Task Management Backend

## Overview

A secure Task Management REST API built using Spring Boot and MySQL.

Users can register, log in using JWT authentication, and manage their own tasks securely.

## Tech Stack

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* JWT
* BCrypt
* Maven

## Features

* User Registration
* User Login
* JWT Authentication
* BCrypt Password Encryption
* Create Task
* Update Task
* Delete Task
* Search Tasks
* Filter Tasks
* Pagination
* DTO Pattern
* Validation
* Global Exception Handling
* User-specific Authorization

## API Endpoints

### User APIs

POST /users

POST /users/login

GET /users/{id}

### Task APIs

POST /tasks

GET /tasks

PUT /tasks/{id}

DELETE /tasks/{id}

GET /tasks/page?page=0&size=2

GET /tasks/filter?status=TODO

GET /tasks/search?title=test

## Security

* JWT Authentication
* BCrypt Password Hashing
* User Ownership Validation

## Author

Nitin
