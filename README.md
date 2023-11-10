# Spring Boot JWT

# Stack

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)

# File structure

```
spring-boot-jwt/
 │
 ├── src/main/java/
 │   └── uomaep
 │       ├── controller
 │       │   ├── MainController.java
 │       │   └── UserController.java
 │       │
 │       ├── dto
 │       │   ├── LoginDTO.java
 │       │   └── UserDTO.java
 │       │
 │       ├── mapper
 │       │   └── UserDataMapper.java
 │       │
 │       ├── model
 │       │   ├── AppUserRole.java
 │       │   └── AppUser.java
 │       │
 │       ├── repository
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   ├── LoginAuthFilter.java
 │       │   ├── LoginAuthProvider.java
 │       │   ├── LoginAuthToken.java
 │       │   ├── PasswordEncoderSHA512.java
 │       │   ├── SecurityConfig.java
 │       │   ├── UserData.java
 │       │   └── UserDataService.java
 │       │
 │       ├── utils
 │       │   └── ResponseJsonWriter.java
 │       │
 │       └── SpringBootJwtApplication.java
 │
 ├── src/main/resources/
 │   ├── mapper
 │   │   └── UserDataMapper.java
 │   ├── templates
 │   │   └── index.html
 │   ├── application.properties
 │   └── application-database.properties (gitignore)
 │
 ├── .gitignore
 ├── build.gradle
 ├── gradlew
 ├── gradlew.bat
 ├── README.md
 └── settings.gradle
```
