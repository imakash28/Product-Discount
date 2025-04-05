# 🛍️ E-Commerce Product Discount API

This is a Spring Boot-based RESTful API that manages product discounts for an e-commerce platform. It supports flat, percentage, and seasonal discounts with validations like stock availability and price integrity.

---

## 🚀 Features

- Add new products to the system.
- Apply flat or percentage-based discounts.
- Additional seasonal discounts.
- Final price validation (never negative).
- Stock check before applying discounts.
- Integrated with PostgreSQL or MySQL.

---

## 🧰 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL / MySQL
- Maven / Gradle
- Swagger (API documentation)

---

## 📦 API Endpoints

### POST `/product/add`
Add a new product.

```json
{
  "name": "Trouser",
  "price": 200,
  "quantity": 7
}

#Response
{
    "id": 5,
    "name": "Trouser",
    "price": 200,
    "quantity": 7
}


---
POST `/product/discount`
Apply dicount on product.

```json
{
  "productId": "1",
  "discountType": "percentage", 
  "discountValue": 10, 
  "seasonalDiscountActive": true,
  "productPrice": 100, 
  "quantity": 5
}

#Response
65.00



GET /product/{productId} productId=4
Get product details (including final discounted price if applied).

#Response
{
    "id": 5,
    "name": "Trouser",
    "price": 200.00,
    "quantity": 7
}



⚙️ Configuration
PostgreSQL (in application.properties)

spring.datasource.url=jdbc:postgresql://localhost:5432/discountdb
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
spring.jpa.hibernate.ddl-auto=update

MySQL (alternative application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/discountdb
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
spring.jpa.hibernate.ddl-auto=update

▶️ How to Run
Clone the repository.

Configure database in application.properties.

Run the app:

./mvnw spring-boot:run
or
./gradlew bootRun

4. Test APIs using Swagger at:
http://localhost:8080/swagger-ui/index.html





