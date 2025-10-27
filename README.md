# Hospital Management System (HMS) 🏥

A secure Hospital Management System built with Spring Boot, JWT Authentication, MySQL, and Swagger UI for API documentation.

# Table of Contents

Overview

Features

Technologies

Database

API Documentation

Setup & Installation

Folder Structure

Security

CORS Configuration

Contact

# Overview

# HMS manages hospital operations efficiently:

User registration & login

Role-based access for USER and ADMIN

Doctor, patient, appointment, and medical record management

JWT-based stateless security

API testing via Swagger UI

# Features

Authentication & Authorization

User registration and login

JWT token-based security

Role-based access control

Doctor Management

List doctors & specializations

Add, update, delete (ADMIN only)

Patient Management

View patient records

Schedule appointments

Manage medical records

Appointment Management

Book & track appointments

API Documentation

Swagger UI for testing APIs

Security

Passwords encrypted using BCrypt

Stateless session management

CORS configured for development

# Technologies

Backend: Java 21, Spring Boot 3.5.7, Spring Security, Spring Data JPA (Hibernate)

Database: MySQL 8.x

Security: JWT Authentication, BCrypt Password Encoder

API Documentation: Swagger UI (springdoc-openapi)

Other: Maven, Lombok, Hibernate Validator

# Database

Database Name: hms_db

# Tables:

users – user credentials & roles

doctors – doctor info & specialization

patients – patient info

appointments – scheduled appointments

medical_records – patient history

# Connection (application.properties):

spring.datasource.url=jdbc:mysql://localhost:3306/hms_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# API Documentation

Swagger UI is available at:

http://localhost:8080/swagger-ui.html


# Public Endpoints:

/api/auth/** – Authentication (login)

/api/users/register – User registration

/api/doctors/list – List doctors

/api/doctors/specializations – List specializations

Admin Endpoints:

/api/doctors/add

/api/doctors/update/{id}

/api/doctors/delete/{id}

Setup & Installation

Clone the repository:

git clone <repository_url>


# Import project into IDE (IntelliJ IDEA / VS Code)

Create MySQL database hms_db

Update credentials in application.properties

Build and run the project:

mvn clean install
mvn spring-boot:run


# Open Swagger UI to test APIs:

http://localhost:8080/swagger-ui.html

# Folder Structure
hms/
├── src/main/java/com/management/hms/hms/
│   ├── config/          # Swagger & App configurations
│   ├── controller/      # REST Controllers
│   ├── entity/          # Database entities
│   ├── repository/      # Spring Data JPA Repositories
│   ├── security/        # Security configuration & JWT
│   ├── service/         # Service layer
│   └── HmsApplication.java
└── src/main/resources/
    ├── application.properties
    └── static/

# Security

Password Encoder: BCrypt

Authentication: JWT token

Authorization: Role-based (USER / ADMIN)

Session Management: Stateless

Filter: JwtAuthenticationFilter intercepts requests

# CORS Configuration
configuration.setAllowedOrigins(Arrays.asList("*"));
configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
configuration.setAllowedHeaders(Arrays.asList("*"));
configuration.setAllowCredentials(true);
configuration.setMaxAge(3600L);
