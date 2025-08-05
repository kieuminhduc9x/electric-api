# ⚡ Power Grid Management System - Backend API

A comprehensive Spring Boot REST API for managing electrical power grid infrastructure, real-time monitoring, alerts, and maintenance operations.

## 🏗️ System Architecture

```
Frontend (React) ↔ REST API (Spring Boot) ↔ Database (MySQL)
                 ↔ WebSocket (Real-time) ↔ Redis (Cache)
                 ↔ File Storage (Images/Documents)
```

## ✨ Features

### 🔐 Authentication & Authorization
- **JWT-based authentication** with refresh tokens
- **Role-based access control** (Admin, Operator, User, Viewer)
- **Login history tracking** with IP, device, and browser info
- **Password encryption** using BCrypt

### 🔌 Device Management
- **Complete device lifecycle** management
- **Device types** and **manufacturers** categorization
- **Station-based organization** with GPS coordinates
- **Real-time status monitoring** (Online/Offline/Maintenance/Error)
- **Serial number tracking** and specifications

### 📊 Real-time Monitoring
- **Live sensor data** (voltage, current, power, frequency, temperature)
- **WebSocket connections** for real-time updates
- **Historical data storage** with time-series optimization
- **Data aggregation** for reports and analytics

### 🚨 Alert System
- **Auto alert generation** based on configurable thresholds
- **Alert levels** (Info, Warning, High, Critical) with severity
- **Alert workflow** (New → In Progress → Resolved/Cancelled)
- **Email notifications** for critical alerts

### 🔧 Maintenance Management
- **Maintenance scheduling** (Preventive, Corrective, Emergency, Inspection)
- **File upload support** for photos and documents
- **Technician assignment** and supervisor tracking
- **Cost tracking** and duration monitoring
- **MTBF calculations** and failure analysis

### 📈 Reports & Analytics
- **Power consumption reports** with time-series data
- **Device status statistics** and availability metrics
- **Alert trend analysis** and failure patterns
- **MTBF reports** and maintenance effectiveness

### ⚙️ System Configuration
- **Configurable thresholds** for voltage, current, temperature
- **System settings** for polling intervals and defaults
- **Category management** for device types, manufacturers, alert levels

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.4
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0 (H2 for testing)
- **Cache**: Redis
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Gradle
- **Java Version**: 21

## 📦 Dependencies

```gradle
// Core Spring Boot
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-websocket
spring-boot-starter-validation
spring-boot-starter-mail
spring-boot-starter-data-redis
spring-boot-starter-cache

// JWT Authentication
io.jsonwebtoken:jjwt-api:0.12.3
io.jsonwebtoken:jjwt-impl:0.12.3
io.jsonwebtoken:jjwt-jackson:0.12.3

// API Documentation
springdoc-openapi-starter-webmvc-ui:2.2.0

// Database
mysql-connector-java:8.0.33
h2database (for testing)
```

## 🗄️ Database Schema

### Core Entities
- **Users** - Authentication and user management
- **Devices** - Electrical equipment in the grid
- **DeviceData** - Real-time sensor readings
- **Alerts** - System notifications and warnings
- **Maintenance** - Service records and scheduling
- **Stations** - Physical locations/substations

### Supporting Entities
- **DeviceTypes** - Equipment categories
- **Manufacturers** - Equipment vendors
- **AlertLevels** - Severity classifications
- **AlertStatuses** - Workflow states
- **SystemConfigs** - Application settings

## 🚀 Quick Start

### Prerequisites
- Java 21+
- MySQL 8.0+ (optional, H2 embedded for testing)
- Redis (optional, for caching)

### 1. Clone and Setup
```bash
git clone <repository-url>
cd electric-api
```

### 2. Configure Database
Edit `src/main/resources/application.properties`:
```properties
# MySQL Configuration (Primary)
spring.datasource.url=jdbc:mysql://localhost:3306/powergrid
spring.datasource.username=root
spring.datasource.password=your_password

# H2 Configuration (Fallback/Testing)
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.h2.console.enabled=true
```

### 3. Run the Application
```bash
# Using Gradle Wrapper
./gradlew bootRun

# Or build and run JAR
./gradlew build
java -jar build/libs/electric-api-0.0.1-SNAPSHOT.jar
```

### 4. Access the API
- **API Base URL**: http://localhost:8080/api
- **Swagger Documentation**: http://localhost:8080/swagger-ui.html
- **H2 Console** (if enabled): http://localhost:8080/h2-console

## 🔑 Authentication

### Demo Accounts
| Email | Password | Role | Description |
|-------|----------|------|-------------|
| admin@powergrid.com | admin123 | ADMIN | Full system access |
| operator@powergrid.com | operator123 | OPERATOR | Operations management |
| user@powergrid.com | user123 | USER | Standard user access |
| viewer@powergrid.com | viewer123 | VIEWER | Read-only access |

### Login Request
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@powergrid.com",
    "password": "admin123"
  }'
```

### JWT Token Usage
```bash
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     http://localhost:8080/api/devices
```

## 📋 API Endpoints

### Authentication
```
POST /api/auth/login       - User authentication
POST /api/auth/register    - New user registration
POST /api/auth/refresh     - Refresh JWT token
POST /api/auth/logout      - User logout
```

### User Management (Admin only)
```
GET    /api/users          - List all users
POST   /api/users          - Create new user
GET    /api/users/{id}     - Get user details
PUT    /api/users/{id}     - Update user
DELETE /api/users/{id}     - Delete user
```

### Device Management
```
GET    /api/devices        - List devices
POST   /api/devices        - Create device
GET    /api/devices/{id}   - Get device details
PUT    /api/devices/{id}   - Update device
DELETE /api/devices/{id}   - Delete device
GET    /api/devices/{id}/data - Real-time data
```

### Alert Management
```
GET    /api/alerts         - List alerts
POST   /api/alerts         - Create alert
GET    /api/alerts/{id}    - Get alert details
PUT    /api/alerts/{id}    - Update alert
DELETE /api/alerts/{id}    - Delete alert
```

### Maintenance Management
```
GET    /api/maintenance     - List maintenance records
POST   /api/maintenance     - Create maintenance
GET    /api/maintenance/{id} - Get maintenance details
PUT    /api/maintenance/{id} - Update maintenance
POST   /api/maintenance/{id}/files - Upload files
```

### Reports & Analytics
```
GET    /api/reports/power-data    - Power consumption reports
GET    /api/reports/device-status - Device status statistics
GET    /api/reports/alert-summary - Alert analytics
POST   /api/reports/export        - Export reports (PDF/Excel)
```

## 🔧 Configuration

### Application Settings
Key configuration options in `application.properties`:

```properties
# JWT Configuration
jwt.secret=your-secret-key
jwt.expiration=86400000              # 24 hours
jwt.refresh-expiration=604800000     # 7 days

# File Upload
spring.servlet.multipart.max-file-size=10MB
file.upload-dir=uploads/

# System Thresholds (via database)
over_voltage_threshold=240.0
over_current_threshold=40.0
over_temperature_threshold=80.0
data_polling_interval=60
```

### Environment-specific Profiles
Create additional property files for different environments:
- `application-dev.properties`
- `application-staging.properties`
- `application-prod.properties`

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

### API Testing with Swagger
Visit http://localhost:8080/swagger-ui.html for interactive API testing.

### Sample Data
The application includes a comprehensive data seeder that creates:
- 4-6 power stations across Vietnam
- 15-20 devices per station
- 100+ sample alerts
- System configuration defaults
- Demo user accounts

## 📊 Performance Features

- **Database Indexing** on frequently queried fields
- **Redis Caching** for user sessions and device data
- **Connection Pooling** for database connections
- **Pagination** support for all list endpoints
- **Async Processing** for notifications and reports
- **Rate Limiting** for API protection

## 🔒 Security Features

- **JWT Authentication** with secure token handling
- **Password Encryption** using BCrypt
- **Role-based Authorization** at method level
- **CORS Configuration** for frontend integration
- **SQL Injection Protection** via JPA/Hibernate
- **Request Validation** using Bean Validation

## 🌐 Frontend Integration

This API is designed to work with a React frontend. Key integration points:

### CORS Configuration
```properties
app.cors.allowed-origins=http://localhost:3000,http://localhost:3001
app.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
app.cors.allowed-headers=*
app.cors.allow-credentials=true
```

### WebSocket Endpoints
```
/ws/real-time-data    - Device data updates
/ws/alerts           - New alert notifications
/ws/notifications    - System notifications
```

## 📝 Development

### Project Structure
```
src/main/java/com/example/electric_api/
├── config/          # Configuration classes
├── controller/      # REST API controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── repository/     # Data access interfaces
├── security/       # JWT and security components
└── service/        # Business logic services
```

### Adding New Features
1. Create entity classes in `entity/`
2. Add repository interfaces in `repository/`
3. Implement business logic in `service/`
4. Create REST controllers in `controller/`
5. Add DTOs for request/response in `dto/`

## 📚 Documentation

- **API Documentation**: Available at `/swagger-ui.html`
- **Database Schema**: Auto-generated via Hibernate DDL
- **Endpoint Documentation**: Comprehensive OpenAPI 3 specs

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For questions and support:
- Create an issue in the repository
- Check the Swagger documentation
- Review the application logs for debugging

---

**⚡ Built for the future of power grid management** 🏗️ 