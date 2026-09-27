# demo-api

Spring Boot 4.1.1 / Java 17 demo API used to exercise the API compatibility gate.
It is deliberately small: three endpoints, two DTOs, and an in-memory map.

## The demo contract

`springdoc-openapi` derives the OpenAPI contract from the code, so nothing has to be
maintained by hand:

* contract: `GET /v3/api-docs`
* Swagger UI: `/swagger-ui.html`

| Method | Path          | Request body                       | Responses                 |
| ------ | ------------- | ---------------------------------- | ------------------------- |
| GET    | `/users`      | –                                  | `200` `User[]`            |
| GET    | `/users/{id}` | –                                  | `200` `User`, `404`       |
| POST   | `/users`      | `CreateUserRequest` (`name`,`email`) | `201` `User`, `400`     |

The contract is port-independent: the engine ignores the generated `servers` entry, so
running the same revision on a different port never shows up as a change.

## Running locally

```bash
./mvnw spring-boot:run                       # http://localhost:8080/v3/api-docs
./mvnw clean package && java -jar target/demo-api-0.0.1-SNAPSHOT.jar --server.port=8081
```
