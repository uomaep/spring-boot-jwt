# Spring Boot JWT

# Stack

![](https://img.shields.io/badge/java_17-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mybatis-✓-blue.svg)
![](https://img.shields.io/badge/aws-✓-blue.svg)
![](https://img.shields.io/badge/docker--compose-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)

# AWS

- ubuntu freetier ec2

# Docker Compose

```
aws/
 │
 └── ~/project
     └── spring-boot-jwt
         ├── mysql
             ├── .env
             └── docker-compose.yml
```

```docker-compose.yml
# docker-compose.yml
version: '3.8'
services:
  mysql:
    container_name: blovi_dev_db
    image: mysql:latest
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
      MYSQL_DATABASE: ${DB_DATABASE}
      MYSQL_USER: ${DB_USER}
      MYSQL_PASSWORD: ${DB_PASSWORD}
    ports:
      - "33306:3306"
    volumes:
      - ./mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

```.env
# .env
DB_USER=
DB_PASSWORD=
DB_DATABASE=
```

```shell
sudo docker compose up -d #백그라운드 실행
```

### ec2 인스턴스 보안 설정에서 인바운드 규칙 설정
- mysql 서버 port (docker-compose.yml에 설정한 port[33306])


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

### JWT
- 인증에 필요한 정보들을 암호화시킨 JSON 토큰
- JWT 토큰(Access Token)을 HTTP 헤더에 실어 서버가 클라이언트를 식별하는 방식
- 토큰 내부에는 위변조 방지를 위해 개인키를 통한 전자서명도과 해당 사용자의 정보가 들어있음 &rarr; 사용자가 JWT를 서버로 전송하면 서버는 서명을 검증하는 과정을 거치게 되며 검증이 완료되면 요청한 응답을 돌려주는 방식
- Access Token의 유효기간을 짧게 설정하여 혹여나 탈취 당하더라도 그 위험성을 낮출 수 있음.
- Refresh Token은 Access Token이 만료되었을 경우 새로운 Access Token을 발급해주기 위해 사용하는 토큰, 보통 DB에 저장.

&rarr; 즉, Access Token은 접근에 관여하는 토큰, Refresh Token은 재발급에 관여하는 토큰의 역할이다.



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

### 스크린샷
<img width="648" alt="스크린샷 2023-11-11 오전 7 20 42" src="https://github.com/uomaep/spring-boot-jwt/assets/114221785/54de4356-fe19-4a3f-982f-82ade12a13f8">

#### 로그인 성공 시
<img width="450" alt="스크린샷 2023-11-11 오전 7 21 06" src="https://github.com/uomaep/spring-boot-jwt/assets/114221785/cbd6e9f3-11f3-40ad-9ae2-b090d29e6794">

#### 로그인 실패 시
<img width="465" alt="스크린샷 2023-11-11 오전 7 17 32" src="https://github.com/uomaep/spring-boot-jwt/assets/114221785/38234acd-c2c9-44af-aa77-8efa5c31ebfa">

<img width="450" alt="스크린샷 2023-11-11 오전 7 21 39" src="https://github.com/uomaep/spring-boot-jwt/assets/114221785/337c064a-2a4c-4bd8-9a95-5c55c41c510c">


#### DB에 저장되는 password는 email+password의 해시값, 그러니 비교도 그렇게..
<img width="1008" alt="스크린샷 2023-11-11 오전 6 54 02" src="https://github.com/uomaep/spring-boot-jwt/assets/114221785/f04ea8ea-99b2-4779-ab81-9064b8d5046d">

#### refresh token도 해싱해서 DB에 저장
<img width="990" alt="스크린샷 2023-11-11 오전 6 56 00" src="https://github.com/uomaep/spring-boot-jwt/assets/114221785/9acf36f1-10b8-414d-9686-e06391a40b55">
