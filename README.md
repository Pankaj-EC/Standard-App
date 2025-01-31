# Spring Boot Kotlin Project

This project is a Spring Boot application written in Kotlin that includes the following functionalities:

- MySQL Database Integration
- Redis Caching
- SMTP Mail Service for Email Notifications
- Password Update with OTP (One-Time Password) via Email
- User Registration and Profile Management

## Prerequisites

Before running the application, ensure that you have the following installed:

- [Java 17+](https://jdk.java.net/17/)
- [Kotlin](https://kotlinlang.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [Docker](https://www.docker.com/)

## Getting Started

### Clone the Repository

## Set Up MySQL Database
### Install and start MySQL.
### Create a new database:

sql
Copy code
CREATE DATABASE your_database_name;
Update the application.yml or application.properties file with your MySQL database credentials.

## Set Up Redis
#### To start a Redis Stack container using the redis-stack image, run the following command in your terminal:

```bash
Copy code
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
Redis Insight will be available at http://localhost:8001.

