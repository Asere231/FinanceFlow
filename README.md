# Project Overview

A modular microservices-based financial platform, composed of the following services:

- **auth-service**: Handles user authentication and JWT issuance.
- **account-service**: Manages bank accounts and balances.
- **transaction-service**: Records and queries financial transactions.
- **categories-service**: CRUD and analytics for transaction categories.
- **budgets-service**: Create, track, and reset spending budgets.
- **goals-service**: Track financial goals and contributions.
- **analytics-service**: Aggregated reports (spending by category, cash flow, net worth, etc.).
- **api-gateway**: Unified entry point, routing, and discovery integration.

# Getting Started

## Prerequisites

- Java 21
- Maven
- (Optional) Docker & Docker Compose
- Service Registry (e.g., Eureka) up and running

## Build & Run

1. Clone the repo.
2. Configure each service’s `application.yml`/`properties` (ports, service discovery URL).
3. From each service directory:
   ```bash
   mvn clean package && java -jar target/*.jar
   # or with Gradle:
   ./gradlew bootJar && java -jar build/libs/*.jar
