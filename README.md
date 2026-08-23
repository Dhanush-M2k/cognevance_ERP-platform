# cognevance_ERP-platform
A full-stack Java 17 and Spring Boot 3.3 platform featuring secure role-based access control, REST APIs, and server-rendered Thymeleaf views. It integrates five core modules—Employees, Inventory, Sales, Finance, and Feedback—alongside real-time JVM system metrics and Chart.js analytics. Database-ready for H2 and MySQL.
# Enterprise ERP & Business Analytics Platform

A full-stack ERP and business analytics platform built with **Spring Boot** and **plain Java** (no Lombok, no code generators — every getter, setter, and constructor is written by hand). Includes secure authentication, role-based authorization, five business modules, REST APIs, an analytics dashboard, and a live system-monitoring screen.

---

## 1. Architecture

```
Browser (Thymeleaf + modern CSS + Chart.js)
        │
        ▼
Spring MVC Controllers  ──────────────►  REST API Controllers (/api/**)
        │                                        │
        ▼                                        ▼
        Service Layer (business logic, plain Java)
                        │
                        ▼
        Spring Data JPA Repositories
                        │
                        ▼
        Database (H2 in-memory by default → swap to MySQL/PostgreSQL cloud DB)

Cross-cutting: Spring Security (form login, BCrypt, role-based access),
Actuator + java.lang.management (live monitoring), Thymeleaf (server-rendered UI)
```

**Package layout** (`com.erp.platform`):

| Package | Responsibility |
|---|---|
| `model` | JPA entities — `User`, `Employee`, `InventoryItem`, `Sale`, `FinanceRecord`, `Feedback`, `Role` enum |
| `repository` | Spring Data JPA interfaces |
| `service` | Business logic, one service per module, plus `DashboardService` (analytics aggregation) and `MonitoringService` (live JVM metrics) |
| `security` | `CustomUserDetails`, `CustomUserDetailsService` |
| `config` | `SecurityConfig` (auth rules, role-based redirects), `DataInitializer` (seed data) |
| `controller` | Page controllers (Thymeleaf views) |
| `controller.api` | REST API controllers (JSON) |

No Lombok or annotation-processing code generators are used anywhere — all entity/model classes contain explicit fields, constructors, getters, and setters.

---

## 2. Modules

| Module | Description |
|---|---|
| **Auth** | Login, new-user (customer) self-registration, BCrypt password hashing, role-based post-login redirect |
| **Employees** | CRUD for staff records: department, designation, salary, join date, status |
| **Inventory** | Stock items, SKU, category, supplier, reorder-level low-stock alerts |
| **Sales** | Order capture, quantity × unit price auto-totaling, status tracking |
| **Finance** | Income/expense ledger, auto-computed net profit |
| **Feedback & Reviews** | Customer-submitted reviews with a **purchase item** field, 1–5 star **rating**, and free-text **review** field |
| **Dashboard** | Cross-module KPIs and Chart.js analytics (revenue trend, inventory mix, rating distribution) |
| **Live Monitoring** | Real-time CPU load, memory usage, thread counts, JVM info — auto-refreshes every 3 seconds |

### Roles

| Role | Access |
|---|---|
| `ADMIN` | Everything, including Live Monitoring |
| `MANAGER` | Employees, Inventory, Sales, Finance, Feedback, Monitoring |
| `EMPLOYEE` | Inventory, Sales, Feedback |
| `CUSTOMER` | Their own dashboard + Feedback/Reviews only (new sign-ups get this role) |

---

## 3. Demo credentials (seeded automatically on first run)

| Username | Password | Role |
|---|---|---|
| `admin` | `Admin@123` | ADMIN |
| `manager` | `Manager@123` | MANAGER |
| `employee` | `Employee@123` | EMPLOYEE |
| `customer` | `Customer@123` | CUSTOMER |

---

## 4. Running the project

**Prerequisites:** Java 17+, Maven 3.8+ (internet access to Maven Central to download Spring Boot dependencies the first time).

```bash
cd erp-platform
mvn spring-boot:run
```

Then open **http://localhost:8080** — you'll be redirected to `/login`.

To build a runnable jar:
```bash
mvn clean package
java -jar target/erp-platform.jar
```

> This project was authored and reviewed in an offline sandbox without access to Maven Central, so a live `mvn` build could not be executed in that environment. The code has been manually reviewed for syntax and API correctness, but please run `mvn clean package` in your own environment (with internet access) as a first step and report any issue.

---

## 5. Database configuration

By default the app uses an **embedded H2 in-memory database** — zero setup, resets on restart, ideal for demos. Configuration lives in `src/main/resources/application.properties`.

To move to a **cloud database** (AWS RDS, Azure Database for MySQL, GCP Cloud SQL, etc.), comment out the H2 block and uncomment the MySQL block:

```properties
spring.datasource.url=jdbc:mysql://<your-cloud-host>:3306/erp_platform?useSSL=true&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
The MySQL driver is already declared in `pom.xml`, so no dependency changes are needed. `spring.jpa.hibernate.ddl-auto=update` will auto-create/update tables on the target database.

A placeholder `cloud.storage.*` section is also included in `application.properties` for wiring up S3/Blob storage for file/document uploads if you extend the platform.

---

## 6. REST API reference

All endpoints below require an authenticated session (except where noted) and return/accept JSON. Role restrictions mirror the table in section 2.

### Dashboard / Analytics
| Method | Path | Description |
|---|---|---|
| GET | `/api/dashboard/summary` | KPI summary across all modules |
| GET | `/api/dashboard/revenue-series` | Last 6 months revenue, chart-ready |
| GET | `/api/dashboard/inventory-by-category` | Stock quantity grouped by category |
| GET | `/api/dashboard/rating-distribution` | Feedback rating histogram (1–5 stars) |

### Employees (`ADMIN`, `MANAGER`)
| Method | Path | Description |
|---|---|---|
| GET | `/api/employees` | List all employees |
| GET | `/api/employees/{id}` | Get one employee |
| POST | `/api/employees` | Create employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

### Inventory (`ADMIN`, `MANAGER`, `EMPLOYEE`)
| Method | Path | Description |
|---|---|---|
| GET | `/api/inventory` | List all items |
| GET | `/api/inventory/low-stock` | Items at/below reorder level |
| GET | `/api/inventory/{id}` | Get one item |
| POST | `/api/inventory` | Create item |
| PUT | `/api/inventory/{id}` | Update item |
| DELETE | `/api/inventory/{id}` | Delete item |

### Sales (`ADMIN`, `MANAGER`, `EMPLOYEE`)
| Method | Path | Description |
|---|---|---|
| GET | `/api/sales` | List all sales |
| GET | `/api/sales/{id}` | Get one sale |
| POST | `/api/sales` | Create sale (auto-computes total) |
| PUT | `/api/sales/{id}` | Update sale |
| DELETE | `/api/sales/{id}` | Delete sale |

### Finance (`ADMIN`, `MANAGER`)
| Method | Path | Description |
|---|---|---|
| GET | `/api/finance` | List all records |
| GET | `/api/finance/summary` | `{totalIncome, totalExpense, netProfit}` |
| POST | `/api/finance` | Create income/expense record |
| DELETE | `/api/finance/{id}` | Delete record |

### Feedback & Reviews (any authenticated user)
| Method | Path | Description |
|---|---|---|
| GET | `/api/feedback` | List all feedback |
| POST | `/api/feedback` | Submit feedback `{customerName, customerEmail, purchaseItem, rating, reviewText}` |
| DELETE | `/api/feedback/{id}` | Delete a review |

### Live Monitoring (`ADMIN`, `MANAGER`)
| Method | Path | Description |
|---|---|---|
| GET | `/api/monitoring/live` | Current CPU/memory/thread/uptime snapshot |

### Page routes (server-rendered, session-based)
`/`, `/login`, `/register`, `/dashboard`, `/employees`, `/inventory`, `/sales`, `/finance`, `/feedback`, `/monitoring`

---

## 7. Security design

- Passwords hashed with **BCrypt** (`spring-security-crypto`).
- Form login with a role-aware `AuthenticationSuccessHandler` — customers land on `/feedback`, staff land on `/dashboard`.
- URL-level authorization rules defined declaratively in `SecurityConfig` (`authorizeHttpRequests`).
- View-level menu items are additionally hidden per role using the Thymeleaf Spring Security dialect (`sec:authorize`).
- CSRF protection enabled for form pages; disabled for the stateless `/api/**` JSON endpoints (typical for token/AJAX-style APIs — add a bearer-token layer before exposing these publicly).
- `/access-denied` custom page for `403` results.

---

## 8. Performance & scalability notes

- Services are stateless Spring beans — safe for horizontal scaling behind a load balancer.
- `spring.jpa.open-in-view=false` avoids holding DB connections open across the view-rendering phase.
- Swapping H2 for a pooled cloud database (HikariCP is Spring Boot's default pool) is a one-file config change (`application.properties`).
- Actuator (`/actuator/health`, `/actuator/metrics`) is enabled for integration with external monitoring (Prometheus/Grafana, cloud health checks) in addition to the built-in Live Monitoring screen.
- For microservice decomposition, each `service` package is already isolated by module (Employee/Inventory/Sales/Finance/Feedback) and could be extracted into independent Spring Boot services communicating over REST, sharing the same `model` contracts.

---

## 9. Deployment

1. **Package:** `mvn clean package` → produces `target/erp-platform.jar`.
2. **Configure** environment-specific `application.properties` (or override with `--spring.datasource.url=...` / env vars) for your cloud database.
3. **Run:** `java -jar erp-platform.jar --server.port=8080`.
4. **Containerize (optional):**
   ```dockerfile
   FROM eclipse-temurin:17-jre
   COPY target/erp-platform.jar app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```
5. **Cloud targets:** any platform that runs a standard Spring Boot jar (AWS Elastic Beanstalk/ECS, Azure App Service, GCP Cloud Run, Render, Railway, etc.). Point `spring.datasource.*` at your managed database instance as described in section 5.

---

## 10. Tech stack

- Java 17, Spring Boot 3.3 (Web, Security, Data JPA, Validation, Actuator)
- Thymeleaf + thymeleaf-extras-springsecurity6
- H2 (default) / MySQL (cloud-ready)
- Chart.js (via CDN) for analytics visualizations
- Hand-written CSS design system (no CSS framework dependency)
- Plain Java throughout — no Lombok, no MapStruct, no code generation
