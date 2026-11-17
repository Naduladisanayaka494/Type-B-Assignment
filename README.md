# Simple HTTP API — Type B Digital Technical Assessment

A clean, production-ready Java Spring Boot REST API that exposes a `/hello-world` greeting endpoint with alphabetical input validation.

---

## Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.3.5 (`spring-boot-starter-web`)
- **API Documentation:** SpringDoc OpenAPI 3 / Swagger UI (`springdoc-openapi-starter-webmvc-ui`)
- **Build Tool:** Apache Maven
- **Testing:** JUnit 5 (Parameterized Tests) & Spring MockMvc (`spring-boot-starter-test`)

---

## How to Run the Application

### Prerequisites
- **Java 21** (or Java 17+) installed and configured on your `PATH`
- **Apache Maven 3.8+** installed

### Start the Server
From the project root directory, run:

```bash
mvn spring-boot:run
```

Alternatively, package and run the executable JAR:

```bash
mvn clean package
java -jar target/hello-world-api-1.0.0.jar
```

The server starts on `http://localhost:8080`.

### Interactive Testing with Swagger UI
Once the application is running, you can test the endpoint interactively in your browser via **Swagger UI**:
- **Swagger UI:** http://localhost:8080/swagger-ui.html (or http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON Spec:** http://localhost:8080/v3/api-docs

### Example Requests

1. **Valid Input (First half of the alphabet `A–M` or `a–m`):**
   ```bash
   curl -i "http://localhost:8080/hello-world?name=alice"
   ```
   **Response (`200 OK`):**
   ```json
   {
     "message": "Hello Alice"
   }
   ```

2. **Invalid Input (Second half of the alphabet `N–Z` or `n–z`):**
   ```bash
   curl -i "http://localhost:8080/hello-world?name=nathan"
   ```
   **Response (`400 Bad Request`):**
   ```json
   {
     "error": "Invalid Input"
   }
   ```

3. **Missing or Empty Parameter:**
   ```bash
   curl -i "http://localhost:8080/hello-world"
   curl -i "http://localhost:8080/hello-world?name="
   ```
   **Response (`400 Bad Request`):**
   ```json
   {
     "error": "Invalid Input"
   }
   ```

---

## How to Run the Tests

Execute the full unit and integration test suite using Maven:

```bash
mvn clean test
```

The test suite covers:
- All letters in the first half of the English alphabet (`A–M` and `a–m`), including boundary letters (`A`, `M`, single-character names, lowercase and capitalized names).
- All letters in the second half of the English alphabet (`N–Z` and `n–z`), verifying `400 Bad Request` and `{"error": "Invalid Input"}`.
- Edge cases: `null`, missing query parameter, empty string `""`, whitespace-only strings (`"   "`, `"\t"`), surrounding whitespace trimming (`"  alice  "`), numbers (`"123"`), symbols (`"@alice"`), and non-ASCII characters (`"Élodie"`).

---

## Assumptions Made

1. **Name Capitalization:** Based on the specification example (`GET /hello-world?name=alice` returning `{ "message": "Hello Alice" }`), the API capitalizes the first letter of valid names while preserving the casing of subsequent characters (e.g., `alice` -> `Alice`, `mcDonald` -> `McDonald`).
2. **Whitespace Handling:** Leading and trailing whitespace in the `name` query parameter is trimmed prior to validation and formatting (e.g., `?name=%20alice%20` is treated as `alice`). If the parameter consists solely of whitespace, it is treated as empty and rejected with `400 Bad Request` (`{ "error": "Invalid Input" }`).
3. **Non-Alphabetic Characters:** Since a valid `name` must start with a letter in the first half of the English alphabet (`A–M` or `a–m`), any input starting with digits, punctuation, or non-ASCII characters is rejected with `400 Bad Request` and `{ "error": "Invalid Input" }`.
