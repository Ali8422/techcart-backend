# TechCart Enterprise Backend - Project Context & Roadmap

## 📌 Project Overview
- **Tech Stack**: Java 17+, Spring Boot 3.x, Spring Data JPA, Spring Security, PostgreSQL/H2, Docker
- **Architecture**: Layered Architecture (Controller -> Service -> Repository -> Entity)
- **Design Principles**: SOLID (SRP, OCP focus), DTO pattern, Exception handling via @ControllerAdvice

---

## 🗺️ Phase Tracker

| Phase | Description |    Status     | Current Branch |
| :--- | :--- |:-------------:| :--- |
| **Phase 1** | Project Setup & Architecture Foundations |      🟢 COMPLETED       | `feature/phase1-backend-setup` |
| **Phase 2** | Database Design, JPA Entities & Persistence | 🟡 IN PROGRESS | `feature/phase2-jpa-entities` |
| **Phase 3** | Service Layer & SOLID Business Logic | ⚪ NOT STARTED | - |
| **Phase 4** | DTOs, Mapper Layer & REST API Controllers | ⚪ NOT STARTED | - |
| **Phase 5** | Security, JWT Authentication & Authorization | ⚪ NOT STARTED | - |
| **Phase 6** | Global Exception Handling & Request Validation | ⚪ NOT STARTED | - |
| **Phase 7** | Production Containerization & Integration | ⚪ NOT STARTED | - |

---

## 📁 Package Architecture Plan
`com.techcart`
├── `config/`         # Security, CORS, Database Configs
├── `controller/`     # REST Endpoints
├── `dto/`            # Request & Response Data Transfer Objects
├── `entity/`         # JPA Database Entities
├── `exception/`      # Custom Exceptions & Global Exception Handler
├── `repository/`     # Spring Data JPA Interfaces
├── `service/`        # Business Logic Interfaces & Implementations
└── `strategy/`       # Payment & Notification Strategies (OCP/SRP)