# WebFlux intro
An introduction to reactive programming with Spring

### Build

```sh
./gradlew clean build
```

### Run with mongo (in mem db)

```sh
./gradlew bootRun
```

### Run with H2 (in mem db)

```sh
./gradlew bootRun -Dspring.profiles.active=h2
```

### Swagger api docs

http://localhost:8080/api/docs
