# E-Charging Points API

This project implements a RESTful API that provides information about e-charging points, as specified in the provided Swagger specification (v3.0). The API is built using Spring Boot and adheres strictly to the provided API definitions.

## Project Structure

- **Controllers:**
    - `ChargingPoolController`: Manages operations related to charging pools.
    - `ChargePointStatusController`: Handles operations for charge point statuses and dynamic data retrieval.

- **Services:**
    - `ChargingPoolService`: Business logic for managing charging pools.
    - `ChargePointStatusService`: Business logic for managing charge point statuses and dynamic data.

- **DTOs and Entities:**
    - `ChargingPoolDTO`, `PoolSearchRequest`, `PoolSearchResponse`, etc.: Represent the request and response models as defined in the Swagger specification.
    - `ChargingPool`, `PoolLocation`, `WGS84Point`, etc.: Represent the underlying data structures stored in the database.

- **Exception Handling:**
    - `GlobalExceptionHandler`: Centralized exception handling to return error responses as defined in the Swagger specification.

## Compliance with Swagger Specification

### Endpoints
The following endpoints have been implemented according to the Swagger spec:

- **POST `/api/charging-pools/pool-find`**: Returns a list of pools based on provided IDs and optional filters.
- **POST `/api/charge-point-status/availability/charge-points`**: Returns dynamic data for a list of charge points.
- **GET/POST/DELETE `/api/charge-point-status/{chargePointId}`**: Manages retrieval, creation, and deletion of charge point statuses.
- **GET/PUT/DELETE `/api/charging-pools/{id}`**: Manages retrieval, updating, and deletion of charging pools by ID.
- **POST `/api/charging-pools`**: Handles the creation of new charging pools.

### Request and Response Models
- All request and response models defined in the Swagger spec (e.g., `PoolSearchRequest`, `ChargePointDynStatusResponseList`) are fully implemented.
- Complex types like `WGS84Point`, `PoolLocation`, and `ChargingStation` are properly handled.

### Error Handling
- Proper error responses (`400`, `401`, `403`, `404`, `503`) are implemented.
- Error details are returned in the format specified in the Swagger documentation.

### Validation
- Input validation is enforced using annotations (`@NotBlank`, `@Min`, etc.).
- All validation rules specified in the Swagger documentation are implemented and tested.

## Testing

### Unit Tests
- Comprehensive unit tests for all services, ensuring business logic correctness.

### Integration Tests
- Extensive integration tests covering all API endpoints.
- Tests include both success and failure scenarios to ensure robust error handling.

### How to Run the Tests
- Tests can be run using Maven: `mvn test`.
- Integration tests require a running instance of Redis and a PostgreSQL database.

## Running the Application

1. Ensure Redis and PostgreSQL are running on the default ports.
2. Build the project using Maven: `mvn clean install`.
3. Run the application: `mvn spring-boot:run`.

## Conclusion

This project meets all the requirements defined in the Swagger specification. The API is fully tested, with comprehensive coverage for both functionality and error handling. The codebase adheres to best practices and is ready for deployment in a production environment.