# Food Log Server

## 프로젝트 구조

```
foodlog
├── api
├── config
├───├── auth
├── domain
├── dto
├───├── response
├───├── request
├── repository
└── service
```


## 개발 환경

Spring Boot | 2.6.7

언어 | Java 11

타입 | Gradle

JDK | version 11.0

Group | dku.capstone

Name | food-log

DB | mariaDB

Dependencies
- Spring Web
- Spring Boot Devtools
- Lombok
- Spring Data JPA
- Spring Security
- OAuth2 Client

<br>

``application.yml``

```
spring:
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/food_log
    username: 아이디
    password: 비밀번호

  jpa:
    hibernate:
      ddl-auto: create
      format_sql: true
      show-sql: true
```