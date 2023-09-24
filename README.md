# EduQuiz

EduQuiz - an application for creating and solving educational quizzes individually or in a group on time.

## Table of Contents

* [General Information](#general-information)
* [Technologies Used](#technologies-used)
* [Prerequisites](#prerequisites)
* [Setup](#setup)
* [Authors](#authors)

## General Information

The project aims to broaden the knowledge and skills acquired at Tomasz Woliński's
courses - [KierunekJava](https://kierunekjava.pl/). EduQuiz is a platform for creating and conducting interactive
quizzes. It is an excellent tool for consolidating knowledge individually or in a group.

## Technologies Used

### Development

- [Java 19](https://openjdk.org/projects/jdk/19/)
- [Spring Boot 3.0.2](https://spring.io/projects/spring-boot)
- [Spring Data](https://spring.io/projects/spring-data)
- [Maven 3.x](https://maven.apache.org/)
- [Git](https://git-scm.com/)

### Test

- [JUnit5](https://junit.org/junit5/)

## Prerequisites

The following tools are required to start the application:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Java 19](https://openjdk.org/projects/jdk/19/)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- [Lombok](https://projectlombok.org/)
- [H2](https://www.h2database.com/html/main.html)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)


## Setup

Before run application you should configurate your database and db server in few step:

- Create database connection with Docker pasting in command line:

docker run --name eduquizdev -e POSTGRES_PASSWORD=password -d -p 5432:5432 eduquizdev

- Connect with server:

Login: postgres

Password: password

## API Documentation

The API documentation can be accessed after starting the project at http://localhost:8080/swagger-ui/index.html

## Authors

Created by StormIT community:

- [JacekRG](https://github.com/JacekRG)
- [MichalMarciniakGS](https://github.com/MichalMarciniakGS)
- [RobertoJavaDev](https://github.com/RobertoJavaDev)
- [Manes79](https://github.com/Manes79)
- [BartoszKarp](https://github.com/BartoszKarp)
- [smario-7](https://github.com/smario-7)
- [tyrontundrom](https://github.com/tyrontundrom)
- [Margeb](https://github.com/Margeb)
- [k4t](https://github.com/k4t4u)
