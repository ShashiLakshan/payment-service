# Payment Microservice

This is a Spring Boot 3.3.2 application that uses H2 as its database.
Payment can be saved with Payment Id, Booking Id, Payment Date, Payment Status and Payment Amount. After payment success, it will be acknowledged by a notification

Other related microservices:
* Event Microservice
* Booking Microservice
* Notification Microservice

#### Swagger URL: http://localhost:8083/swagger-ui/index.html
#### Postman Collection: https://github.com/ShashiLakshan/event-service/blob/main/postman/event-booking.postman_collection.json

## Prerequisites

- Java 22 or later
- Maven 3.8.1 or later
- Docker (for Kafka)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/ShashiLakshan/payment-service.git
cd payment-service
```

### Building the Project
```bash
mvn clean install
```
### Running the Application
```bash
mvn spring-boot:run
```
The application will start and be accessible at http://localhost:8083.

## REST APIs

### POST /api/v1/payments
#### Request
```json
{
  "bookingId": 1,
  "paymentAmt": 400
}
```
#### Response
```json
{
  "paymentId": 1,
  "bookingId": 1,
  "paymentAmt": 400
}
```
