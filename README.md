# Payroll Service — Human Resources System

REST microservice for payroll and employee lifecycle management. I designed and developed this service from scratch as my responsibility within a collaborative academic microservices project.

The module manages contracts, schedules, licenses, vacations, payroll records, and deductions. It integrates with the User Management service to associate employees with active contracts and forwards the caller's authorization token when generating monthly payroll.

## Main capabilities

- Employee contract creation, update, consultation, and termination.
- Contract type, schedule, license type, and deduction type queries.
- License and vacation request workflows with approval, rejection, cancellation, activation, and termination states.
- Scheduled status transitions for contracts, licenses, and vacations.
- Monthly payroll generation for active contracts.
- Automatic health and pension deductions, each calculated at 4% in the implemented academic business rule.
- User Management integration through HTTP with authorization-header forwarding.
- Consistent validation and centralized exception handling.
- OpenAPI documentation with Swagger UI.
- Unit and controller tests with JUnit 5 and Mockito, plus JaCoCo reporting.
- MySQL persistence and containerized local deployment.

## Architecture and integration

```text
Client / API Gateway
        |
        v
Payroll Service  ---- HTTP + JWT ---->  User Management Service
        |
        v
      MySQL
```

The project uses the external Docker network `red_microservicios` so that the Payroll Service, Gateway, and User Management service can communicate by container name.

## API overview

| Domain | Base path | Main operations |
|---|---|---|
| Payroll | `/api/payrolls` | List, find, and generate monthly payroll |
| Contracts | `/contracts` | Create, list, update, and terminate |
| Contract types | `/contract-types` | List and find |
| Schedules | `/schedules` | List and find |
| Licenses | `/licenses` | Request and manage workflow states |
| License types | `/license-types` | List and find |
| Vacations | `/vacations` | Request and manage workflow states |
| Deduction types | `/deduction-types` | List and find |

Swagger UI exposes the complete endpoint, request, and response definitions when the application is running.

## Payroll rule implemented

The `POST /api/payrolls/generate` operation:

1. Retrieves employee-to-contract relationships from User Management using the received authorization token.
2. Validates that execution occurs on the last implemented business day of the month.
3. Selects active contracts with a matching employee.
4. Calculates health and pension deductions at 4% each.
5. Persists the payroll, individual deductions, and their relationships.

This calculation reflects the scope and simplified rules of the academic project; it is not intended as production payroll or legal/tax software.

## Technology stack

- Java 17
- Spring Boot 3.2
- Spring Web, Spring Data JPA, and Bean Validation
- MySQL 8 and H2 for tests
- OpenAPI / Swagger UI
- MapStruct and Lombok
- JUnit 5, Mockito, and JaCoCo
- Docker and Docker Compose
- Maven

## Run with Docker

### Prerequisites

- Git
- Docker Engine with Docker Compose
- User Management service available when testing cross-service operations

### 1. Clone the repository

```bash
git clone https://github.com/JDRodriguez07/payroll-service.git
cd payroll-service
```

The repository's integration branch is `develop`.

### 2. Create the local environment file

```bash
cp .env.example .env
```

Replace the password placeholders in `.env`. The file is ignored by Git and must never be committed.

### 3. Create the shared network

The network only needs to be created once:

```bash
docker network create red_microservicios
```

If it already exists, Docker will report that fact and you can continue.

### 4. Start the services

```bash
docker compose up -d --build
```

| Service | Local URL / port |
|---|---|
| Payroll API | `http://localhost:1123` |
| Swagger UI | `http://localhost:1123/swagger-ui` |
| Adminer | `http://localhost:1122` |
| MySQL | `localhost:1121` |

These default host ports overlap with the standalone User Service environment. Run the services through the complete multi-service Compose configuration or override the `*_HOST_PORT` variables when both repositories need to run independently on the same machine.

To stop the environment:

```bash
docker compose down
```

## Environment variables

| Variable | Purpose | Default |
|---|---|---|
| `MYSQL_ROOT_PASSWORD` | MySQL root password | Required |
| `DB_NAME` | Payroll database name | `db_payroll_service` |
| `DB_USERNAME` | Application database user | `payroll_service_user` |
| `DB_PASSWORD` | Application database password | Required |
| `USER_MANAGEMENT_BASE_URL` | User Management service URL | `http://backend-usermanagment:8080` |
| `PAYROLL_DB_HOST_PORT` | MySQL host port | `1121` |
| `ADMINER_HOST_PORT` | Adminer host port | `1122` |
| `PAYROLL_API_HOST_PORT` | Payroll API host port | `1123` |
| `TZ` | Container time zone used by scheduled jobs | `America/Bogota` |

## Run tests

The test profile uses an in-memory H2 database:

```bash
cd payroll-module
./mvnw clean test
```

After the test phase, the JaCoCo report is generated under:

```text
payroll-module/target/site/jacoco/index.html
```

## Project context

This service was integrated with an API Gateway and User Management through a shared Docker network. The final academic delivery was functional within the available scope and was recognized by the professor as a solid foundation that could be extended and adapted for a real organization.
