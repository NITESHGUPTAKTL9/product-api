# Country-Based Product API

A Spring Boot service that manages products and discounts and calculates final prices
based on country-specific VAT rules.  
The service guarantees **idempotent and concurrency-safe discount application** using
database-level constraints.

---

## 🧰 Tech Stack

- Java 20
- Spring Boot 3.2
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (database migrations)
- JUnit 5 + MockMvc (concurrency tests)
- Docker & Docker Compose

---

## 📦 Features

- Store products and discounts
- Apply discounts safely under concurrent requests
- Prevent the same discount from being applied more than once per product
- Calculate final price using:

finalPrice = basePrice × (1 - totalDiscount%) × (1 + VAT%)

### Country VAT Rules

| Country  | VAT |
|--------|-----|
| Sweden | 25% |
| Germany | 19% |
| France | 20% |

---

## 🚀 Running the Application

### 1️⃣ Start PostgreSQL using Docker

```bash
docker-compose up -d
PostgreSQL will be available at:


Host: localhost
Port: 5432
Database: productdb
Username: postgres
Password: postgres
2️⃣ Build and Run the Service

./mvnw clean package
./mvnw spring-boot:run
The application starts on:


http://localhost:8080
Flyway automatically creates the database schema on startup.

📡 API Endpoints
➤ GET /products?country={country}
Returns all products for the given country with final prices.

Example

curl "http://localhost:8080/products?country=Sweden"
Response


[
  {
    "id": "p1",
    "name": "iPhone 15",
    "finalPrice": 1012.50
  }
]
➤ PUT /products/{id}/discount
Applies a discount to a product in an idempotent and concurrency-safe manner.

Example

curl -X PUT http://localhost:8080/products/p1/discount \
  -H "Content-Type: application/json" \
  -d '{
    "discountId": "WELCOME10",
    "percent": 10
  }'
✔ Multiple concurrent requests with the same discount ID
✔ Only one record is persisted
✔ No side effects on repeated calls

🧪 Running Tests

./mvnw test
Concurrency Test
Simulates multiple simultaneous HTTP requests

Verifies only one discount record is stored

Confirms database-level idempotency guarantees

🔐 Concurrency & Idempotency Strategy
Enforced using a composite primary key on (product_id, discount_id)

Database rejects duplicate inserts

Application catches DataIntegrityViolationException

No in-memory locks or synchronized blocks

Safe across multiple JVM instances

📂 Project Structure

src/main/java/com/product_api
 ├── controller
 ├── service
 ├── model
 ├── repository
 └── dto

src/test/java/com/product_api
 └── DiscountConcurrencyIT