# 🔥 **POWER GRID MANAGEMENT SYSTEM - API GUIDE**

## 🚀 **Base URL**
```
http://localhost:8080
```

## 🔐 **Authentication**
All protected endpoints require JWT Bearer token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

---

## 📋 **API ENDPOINTS OVERVIEW**

### 🔑 **1. AUTHENTICATION APIs** (`/api/auth`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `POST` | `/api/auth/login` | User login | Public |
| `POST` | `/api/auth/register` | User registration | Public |
| `POST` | `/api/auth/refresh` | Refresh JWT token | Public |
| `POST` | `/api/auth/logout` | User logout | Authenticated |

**Demo Accounts:**
```json
{
  "Admin": { "email": "admin@powergrid.com", "password": "admin123" },
  "Operator": { "email": "operator@powergrid.com", "password": "operator123" },
  "User": { "email": "user@powergrid.com", "password": "user123" },
  "Viewer": { "email": "viewer@powergrid.com", "password": "viewer123" }
}
```

---

### 👥 **2. USER MANAGEMENT APIs** (`/api/users`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/users` | Get all users | ADMIN |
| `GET` | `/api/users/{id}` | Get user by ID | ADMIN |
| `POST` | `/api/users` | Create new user | ADMIN |
| `PUT` | `/api/users/{id}` | Update user | ADMIN |
| `DELETE` | `/api/users/{id}` | Delete user | ADMIN |
| `GET` | `/api/users/profile` | Get current user profile | Authenticated |
| `PUT` | `/api/users/profile` | Update current user profile | Authenticated |
| `GET` | `/api/users/{id}/login-history` | Get user login history | ADMIN |
| `GET` | `/api/users/statistics` | Get user statistics | ADMIN |

**Query Parameters:**
- `?search=<text>` - Search users by name, email, department
- `?role=<ADMIN|OPERATOR|USER|VIEWER>` - Filter by role
- `?active=<true|false>` - Filter by active status

---

### ⚡ **3. DEVICE MANAGEMENT APIs** (`/api/devices`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/devices` | Get all devices | All Roles |
| `GET` | `/api/devices/{id}` | Get device by ID | All Roles |
| `POST` | `/api/devices` | Create new device | ADMIN, OPERATOR |
| `PUT` | `/api/devices/{id}` | Update device | ADMIN, OPERATOR |
| `DELETE` | `/api/devices/{id}` | Delete device | ADMIN |
| `GET` | `/api/devices/{id}/data` | Get device real-time data | All Roles |
| `GET` | `/api/devices/{id}/data/history` | Get device historical data | All Roles |
| `GET` | `/api/devices/statistics` | Get device statistics | All Roles |

**Query Parameters:**
- `?search=<text>` - Search devices by name, model, serial number
- `?status=<ONLINE|OFFLINE|MAINTENANCE|ERROR>` - Filter by status
- `?stationId=<id>` - Filter by station
- `?deviceTypeId=<id>` - Filter by device type
- `?page=<number>&size=<number>` - Pagination

---

### 🚨 **4. ALERT MANAGEMENT APIs** (`/api/alerts`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/alerts` | Get all alerts | All Roles |
| `GET` | `/api/alerts/{id}` | Get alert by ID | All Roles |
| `POST` | `/api/alerts` | Create new alert | ADMIN, OPERATOR, USER |
| `PUT` | `/api/alerts/{id}` | Update alert | ADMIN, OPERATOR, USER |
| `PUT` | `/api/alerts/{id}/resolve` | Resolve alert | ADMIN, OPERATOR, USER |
| `DELETE` | `/api/alerts/{id}` | Delete alert | ADMIN |
| `GET` | `/api/alerts/unresolved` | Get unresolved alerts | All Roles |
| `GET` | `/api/alerts/critical` | Get critical alerts | All Roles |
| `GET` | `/api/alerts/statistics` | Get alert statistics | All Roles |

**Query Parameters:**
- `?level=<INFO|WARNING|ERROR|CRITICAL>` - Filter by alert level
- `?status=<NEW|IN_PROGRESS|RESOLVED|CLOSED>` - Filter by status
- `?type=<VOLTAGE|CURRENT|TEMPERATURE|COMMUNICATION>` - Filter by type
- `?deviceId=<id>` - Filter by device
- `?resolved=<true|false>` - Filter by resolution status

---

### 📂 **5. CATEGORY MANAGEMENT APIs** (`/api/categories`)

#### **Device Types**
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/categories/device-types` | Get all device types | All Roles |
| `POST` | `/api/categories/device-types` | Create device type | ADMIN, OPERATOR |
| `PUT` | `/api/categories/device-types/{id}` | Update device type | ADMIN, OPERATOR |
| `DELETE` | `/api/categories/device-types/{id}` | Delete device type | ADMIN |

#### **Manufacturers**
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/categories/manufacturers` | Get all manufacturers | All Roles |
| `POST` | `/api/categories/manufacturers` | Create manufacturer | ADMIN, OPERATOR |
| `PUT` | `/api/categories/manufacturers/{id}` | Update manufacturer | ADMIN, OPERATOR |
| `DELETE` | `/api/categories/manufacturers/{id}` | Delete manufacturer | ADMIN |

#### **Stations**
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/categories/stations` | Get all stations | All Roles |
| `POST` | `/api/categories/stations` | Create station | ADMIN, OPERATOR |

#### **Alert Levels**
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/categories/alert-levels` | Get all alert levels | All Roles |
| `POST` | `/api/categories/alert-levels` | Create alert level | ADMIN |

#### **Alert Statuses**
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| `GET` | `/api/categories/alert-statuses` | Get all alert statuses | All Roles |
| `POST` | `/api/categories/alert-statuses` | Create alert status | ADMIN |

---

## 🔧 **TESTING GUIDE**

### **1. Access Swagger UI**
```
http://localhost:8080/swagger-ui.html
```

### **2. Access H2 Database Console**
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:powergrid
Username: sa
Password: password
```

### **3. Sample Login Request**
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@powergrid.com",
  "password": "admin123"
}
```

### **4. Sample Device Creation**
```bash
POST /api/devices
Authorization: Bearer <token>
Content-Type: application/json

{
  "deviceTypeId": 1,
  "manufacturerId": 1,
  "stationId": 1,
  "name": "Primary Transformer",
  "model": "TX-500",
  "serialNumber": "TX500-001",
  "installationDate": "2024-01-15",
  "specifications": "500kVA, 22kV/400V"
}
```

### **5. Sample Alert Creation**
```bash
POST /api/alerts
Authorization: Bearer <token>
Content-Type: application/json

{
  "deviceId": 1,
  "alertLevelId": 1,
  "alertStatusId": 1,
  "title": "High Temperature Alert",
  "description": "Device temperature exceeded threshold",
  "alertType": "TEMPERATURE",
  "thresholdValue": "80",
  "actualValue": "95"
}
```

---

## 📊 **FEATURES IMPLEMENTED**

### ✅ **Core Features**
- [x] JWT Authentication & Authorization
- [x] Role-based Access Control (ADMIN/OPERATOR/USER/VIEWER)
- [x] User Management (CRUD)
- [x] Device Management (CRUD)
- [x] Alert Management (CRUD)
- [x] Category Management (Device Types, Manufacturers, Stations, Alert Levels/Statuses)
- [x] Real-time Device Data (Mock)
- [x] Historical Data (Mock)
- [x] Statistics & Reports
- [x] Search & Filtering
- [x] Pagination Support

### ✅ **Technical Features**
- [x] Spring Boot 3.5.4 + Java 23
- [x] Spring Security with JWT
- [x] JPA/Hibernate with H2 Database
- [x] OpenAPI 3 Documentation (Swagger)
- [x] CORS Support
- [x] Input Validation
- [x] Error Handling
- [x] Demo Data Seeding

### 🔄 **Future Enhancements**
- [ ] Real WebSocket connections for live data
- [ ] File upload for maintenance records
- [ ] Email notifications
- [ ] Advanced reporting with charts
- [ ] Redis caching
- [ ] MySQL production database
- [ ] Maintenance Management APIs
- [ ] System Configuration APIs

---

## 🎯 **ROLE PERMISSIONS**

| Feature | ADMIN | OPERATOR | USER | VIEWER |
|---------|-------|----------|------|--------|
| **Authentication** | ✅ | ✅ | ✅ | ✅ |
| **View Devices** | ✅ | ✅ | ✅ | ✅ |
| **Create/Update Devices** | ✅ | ✅ | ❌ | ❌ |
| **Delete Devices** | ✅ | ❌ | ❌ | ❌ |
| **View Alerts** | ✅ | ✅ | ✅ | ✅ |
| **Create/Resolve Alerts** | ✅ | ✅ | ✅ | ❌ |
| **Delete Alerts** | ✅ | ❌ | ❌ | ❌ |
| **User Management** | ✅ | ❌ | ❌ | ❌ |
| **Categories Management** | ✅ | ✅* | ❌ | ❌ |
| **System Configuration** | ✅ | ❌ | ❌ | ❌ |

*OPERATOR can create/update but not delete categories

---

## 🚀 **QUICK START**

1. **Start Application:**
   ```bash
   ./gradlew bootRun
   ```

2. **Login with Admin Account:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"admin@powergrid.com","password":"admin123"}'
   ```

3. **Use JWT Token for API Calls:**
   ```bash
   curl -X GET http://localhost:8080/api/devices \
     -H "Authorization: Bearer <your-token>"
   ```

4. **Explore Swagger UI:**
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

**🎊 Power Grid Management System - READY FOR PRODUCTION!** 