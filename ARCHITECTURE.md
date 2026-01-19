Overview

This service provides APIs to manage products and apply discounts in a concurrency-safe and idempotent manner. It calculates final product prices by applying country-specific VAT and all applicable discounts. The system is designed to remain correct under concurrent requests and multiple application instances.

High-Level Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database


Controller: Exposes REST APIs

Service: Contains business logic and transactional boundaries

Repository: Handles database access

Database: Enforces data consistency and concurrency guarantees

Domain Model
Entities

Product

id

name

basePrice

country

Discount

discountId

percent

ProductDiscount

productId

discountId

appliedAt

A composite primary key on (product_id, discount_id) ensures that the same discount cannot be applied more than once to the same product.

Concurrency & Idempotency Approach

Discount application is made concurrency-safe using database-level constraints.

A composite primary key on product_discounts (product_id, discount_id) prevents duplicate inserts

The discount application flow runs inside a database transaction

Concurrent requests attempting to apply the same discount result in a constraint violation

Constraint violations are safely ignored, making the operation idempotent

This approach avoids in-memory locks and works reliably across multiple application instances.

Price Calculation

Final price is calculated as:

finalPrice = basePrice × (1 - totalDiscount%) × (1 + VAT%)


VAT is applied based on the product’s country.

VAT Mapping
Country	VAT
Sweden	25%
Germany	19%
France	20%
API Sequence Diagrams
GET /products
sequenceDiagram
participant Client
participant Controller
participant Service
participant DB

    Client->>Controller: GET /products?country={country}
    Controller->>Service: fetchProducts()
    Service->>DB: Fetch products and discounts
    Service->>Service: Calculate final prices
    Service-->>Controller: Product list
    Controller-->>Client: 200 OK

PUT /products/{id}/discount
sequenceDiagram
participant Client
participant Controller
participant Service
participant DB

    Client->>Controller: PUT /products/{id}/discount
    Controller->>Service: applyDiscount()
    Service->>DB: Insert discount if not exists
    Service->>DB: Insert product_discount
    DB-->>Service: Success or constraint violation
    Service-->>Controller: OK
    Controller-->>Client: 200 OK

Scalability

Stateless application design

Horizontal scaling supported

Concurrency safety guaranteed by the database

No dependency on in-memory synchronization

Summary

The system ensures correctness, idempotency, and concurrency safety by relying on transactional boundaries and database constraints. This design is simple, scalable, and suitable for production workloads.