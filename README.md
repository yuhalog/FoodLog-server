# Food Log Server

## 프로젝트 구조

```
foodlog
├── api
├── config
├── constant
├── domain
├── dto
├───├── response
├───├── request
├── filter
├── repository
├── security
├── service
└── utils
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

```yaml
spring:
  datasource:
    # h2 사용시
    driver-class-name: org.h2.Driver
    url: jdbc:h2tcp://localhost/./food_log
    # mariadb 사용시
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/food_log
    
    username: 아이디
    password: 비밀번호

  jpa:
    hibernate:
      # RDS 사용시 none으로 반드시 변경해야함
      ddl-auto: create
      format_sql: true
      show-sql: true

  profiles:
    include: oauth #,dev (RDS 사용시 위에있는 datasource 주석 처리하고 ,dev 추가)
```

<br/>

```application-dev.yml```
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none

  datasource:
    url: jdbc:mariadb://food-log.clil1qxczh3f.ap-northeast-2.rds.amazonaws.com:3306/food_log
    username: foodlog
    password: foodlog1234
    driver-class-name: org.mariadb.jdbc.Driver
```

<br/>

```application.yml```

````yaml
google:
  auth:
    url: 'https://oauth2.googleapis.com/'
    scope: 'profile,email'
  login:
    url: 'https://accounts.google.com/o/oauth2/v2/auth'
  redirect:
    # 로컬에서 진행시
    uri: 'http://localhost:8080/google/login/redirect'
    # ec2 서버에서 진행시
#    uri: 'http://ec2-3-39-64-171.ap-northeast-2.compute.amazonaws.com:8080/google/login/redirect'
  client:
    id: '177696185773-g2433eafo6etl3t3qhbrm7e77sd8i8d4.apps.googleusercontent.com'
    secret: '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'

jwt:
  secret-key: "NMA8JPctFuna59f5" # 임의의 키값 (변경 가능)
````