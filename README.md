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

# Spring Security Architecture

![image](https://github.com/uomaep/spring-boot-jwt/assets/114221785/c20d8709-28bf-4067-a9d5-6627cc39ef3a)

### 처리 과정
1. 사용자가 로그인 정보와 함께 인증 요청을 한다.(Http Request)
2. LoginAuthFilter가 요청을 가로채고, 가로챈 정보를 통해 LoginAuthToken의 인증용 객체를 생성한다.
3. AuthenticationManager의 구현체인 ProviderManager에게 생성한 LoginAuthToken 객체를 전달한다.
4. AuthenticationManager는 등록된 LoginAuthProvider을 조회하여 인증을 요구한다.
5. 실제 DB에서 사용자 인증정보를 가져오는 UserDataService(UserDetailService 구현체)에 사용자 정보를 넘겨준다.
6. 넘겨받은 사용자 정보를 통해 DB에서 찾은 사용자 정보인 UserData(UserDetail 구현체) 객체를 만든다.
7. LoginAuthProvider은 UserData를 넘겨받고 사용자 정보를 비교한다.
8. 인증이 완료되면 JWT access token과 refresh token을 발행하여 반환 (JWT 발행은 아직 미구현)

