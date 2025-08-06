# TÀI LIỆU THIẾT KẾ DATABASE - HỆ THỐNG QUẢN LÝ LƯỚI ĐIỆN

## 1. TỔNG QUAN HỆ THỐNG

### 1.1 Mục đích
Hệ thống quản lý lưới điện (Power Grid Management System) được thiết kế để:
- Quản lý thiết bị điện lực (máy biến áp, máy phát điện, cầu dao, v.v.)
- Giám sát trạng thái thiết bị theo thời gian thực
- Quản lý cảnh báo và sự cố
- Theo dõi lịch bảo trì và bảo dưỡng
- Quản lý người dùng và phân quyền
- Hiển thị bản đồ lưới điện

### 1.2 Công nghệ sử dụng
- **Database**: H2 (Embedded), có thể chuyển sang MySQL/PostgreSQL
- **ORM**: Hibernate/JPA
- **Framework**: Spring Boot 3.5.4
- **Java**: JDK 23

## 2. CẤU TRÚC DATABASE

### 2.1 Sơ đồ ERD (Entity Relationship Diagram)

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│     USERS       │    │   DEVICE_TYPES  │    │ MANUFACTURERS   │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ id (PK)         │    │ id (PK)         │    │ id (PK)         │
│ email (UK)      │    │ name (UK)       │    │ name            │
│ password        │    │ description     │    │ description     │
│ name            │    │ icon            │    │ country         │
│ role            │    │ specifications  │    │ website         │
│ phone           │    │ is_active       │    │ contact_info    │
│ department      │    │ created_at      │    │ is_active       │
│ position        │    │ updated_at      │    │ created_at      │
│ avatar_url      │    └─────────────────┘    └─────────────────┘
│ is_active       │              │                      │
│ created_at      │              │                      │
│ updated_at      │              │                      │
└─────────────────┘              │                      │
         │                       │                      │
         │                       ▼                      │
         │              ┌─────────────────┐            │
         │              │    STATIONS     │            │
         │              ├─────────────────┤            │
         │              │ id (PK)         │            │
         │              │ name            │            │
         │              │ location        │            │
         │              │ latitude        │            │
         │              │ longitude       │            │
         │              │ address         │            │
         │              │ is_active       │            │
         │              │ created_at      │            │
         │              └─────────────────┘            │
         │                       │                      │
         │                       │                      │
         ▼                       ▼                      ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  LOGIN_HISTORY  │    │    DEVICES      │    │  ALERT_LEVELS   │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ id (PK)         │    │ id (PK)         │    │ id (PK)         │
│ user_id (FK)    │    │ device_type_id  │    │ name (UK)       │
│ login_time      │    │ manufacturer_id │    │ description     │
│ ip_address      │    │ station_id      │    │ severity        │
│ location        │    │ name            │    │ threshold       │
│ device          │    │ model           │    │ color           │
│ browser         │    │ serial_number   │    │ is_active       │
│ success         │    │ status          │    │ created_at      │
└─────────────────┘    │ latitude        │    └─────────────────┘
         │             │ longitude       │              │
         │             │ installation_date│              │
         │             │ last_maintenance │              │
         │             │ specifications   │              │
         │             │ is_active        │              │
         │             │ created_at       │              │
         │             │ updated_at       │              │
         │             └─────────────────┘              │
         │                      │                       │
         │                      │                       │
         │                      ▼                       │
         │              ┌─────────────────┐             │
         │              │   DEVICE_DATA   │             │
         │              ├─────────────────┤             │
         │              │ id (PK)         │             │
         │              │ device_id (FK)  │             │
         │              │ timestamp       │             │
         │              │ voltage         │             │
         │              │ current         │             │
         │              │ power           │             │
         │              │ frequency       │             │
         │              │ temperature     │             │
         │              │ created_at      │             │
         │              └─────────────────┘             │
         │                      │                       │
         │                      │                       │
         │                      ▼                       │
         │              ┌─────────────────┐             │
         │              │     ALERTS      │             │
         │              ├─────────────────┤             │
         │              │ id (PK)         │             │
         │              │ device_id (FK)  │             │
         │              │ alert_level_id  │             │
         │              │ alert_status_id │             │
         │              │ title           │             │
         │              │ description     │             │
         │              │ alert_type      │             │
         │              │ threshold_value │             │
         │              │ actual_value    │             │
         │              │ created_at      │             │
         │              │ resolved_at     │             │
         │              │ resolved_by_user_id│          │
         │              └─────────────────┘             │
         │                      │                       │
         │                      │                       │
         │                      ▼                       │
         │              ┌─────────────────┐             │
         │              │ ALERT_STATUSES  │             │
         │              ├─────────────────┤             │
         │              │ id (PK)         │             │
         │              │ name (UK)       │             │
         │              │ description     │             │
         │              │ color           │             │
         │              │ priority        │             │
         │              │ is_active       │             │
         │              │ created_at      │             │
         │              └─────────────────┘             │
         │                                              │
         │                                              │
         ▼                                              ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ USER_SESSIONS   │    │MAINTENANCE_RECORDS│   │ SYSTEM_CONFIGS  │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ id (PK)         │    │ id (PK)         │    │ id (PK)         │
│ user_id (FK)    │    │ device_id (FK)  │    │ config_key (UK) │
│ token           │    │ type            │    │ config_value    │
│ expires_at      │    │ priority        │    │ description     │
│ created_at      │    │ status          │    │ data_type       │
└─────────────────┘    │ scheduled_date  │    │ created_at      │
                       │ completed_date  │    │ updated_at      │
                       │ duration_hours  │    └─────────────────┘
                       │ cost            │
                       │ technician      │
                       │ supervisor_email│
                       │ description     │
                       │ work_performed  │
                       │ issues_found    │
                       │ parts_replaced_json│
                       │ next_maintenance_date│
                       │ created_at      │
                       └─────────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │MAINTENANCE_FILES│
                       ├─────────────────┤
                       │ id (PK)         │
                       │ maintenance_record_id (FK)│
                       │ file_name       │
                       │ file_path       │
                       │ file_type       │
                       │ file_size       │
                       │ uploaded_at     │
                       └─────────────────┘
```

## 3. CHI TIẾT CÁC BẢNG

### 3.1 Bảng USERS (Người dùng)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| email | VARCHAR(255) | UK, NOT NULL | Email đăng nhập |
| password | VARCHAR(255) | NOT NULL | Mật khẩu (đã mã hóa) |
| name | VARCHAR(255) | NOT NULL | Tên đầy đủ |
| role | ENUM | NOT NULL | Vai trò: ADMIN, OPERATOR, USER, VIEWER |
| phone | VARCHAR(255) | NULL | Số điện thoại |
| department | VARCHAR(255) | NULL | Phòng ban |
| position | VARCHAR(255) | NULL | Chức vụ |
| avatar_url | VARCHAR(255) | NULL | URL ảnh đại diện |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |
| updated_at | TIMESTAMP | NOT NULL | Thời gian cập nhật |

**Indexes:**
- `idx_users_email` (email)
- `idx_users_role` (role)
- `idx_users_active` (is_active)

### 3.2 Bảng DEVICE_TYPES (Loại thiết bị)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| name | VARCHAR(255) | UK, NOT NULL | Tên loại thiết bị |
| description | TEXT | NULL | Mô tả chi tiết |
| icon | VARCHAR(255) | NULL | Icon hiển thị |
| specifications | TEXT | NULL | Thông số kỹ thuật |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |
| updated_at | TIMESTAMP | NOT NULL | Thời gian cập nhật |

**Indexes:**
- `idx_device_types_name` (name)
- `idx_device_types_active` (is_active)

### 3.3 Bảng MANUFACTURERS (Nhà sản xuất)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| name | VARCHAR(255) | NOT NULL | Tên nhà sản xuất |
| description | TEXT | NULL | Mô tả |
| country | VARCHAR(255) | NULL | Quốc gia |
| website | VARCHAR(255) | NULL | Website |
| contact_info | TEXT | NULL | Thông tin liên hệ |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_manufacturers_name` (name)
- `idx_manufacturers_active` (is_active)

### 3.4 Bảng STATIONS (Trạm điện)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| name | VARCHAR(255) | NOT NULL | Tên trạm |
| location | VARCHAR(255) | NULL | Vị trí |
| latitude | DECIMAL(10,8) | NULL | Vĩ độ |
| longitude | DECIMAL(11,8) | NULL | Kinh độ |
| address | TEXT | NULL | Địa chỉ chi tiết |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_stations_name` (name)
- `idx_stations_location` (latitude, longitude)
- `idx_stations_active` (is_active)

### 3.5 Bảng DEVICES (Thiết bị)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| device_type_id | BIGINT | FK, NOT NULL | Loại thiết bị |
| manufacturer_id | BIGINT | FK, NOT NULL | Nhà sản xuất |
| station_id | BIGINT | FK, NOT NULL | Trạm điện |
| name | VARCHAR(255) | NOT NULL | Tên thiết bị |
| model | VARCHAR(255) | NOT NULL | Model |
| serial_number | VARCHAR(255) | UK, NOT NULL | Số serial |
| status | ENUM | NOT NULL | Trạng thái: ONLINE, OFFLINE, MAINTENANCE, ERROR, WARNING |
| latitude | DECIMAL(10,8) | NULL | Vĩ độ |
| longitude | DECIMAL(11,8) | NULL | Kinh độ |
| installation_date | DATE | NULL | Ngày lắp đặt |
| last_maintenance | DATE | NULL | Ngày bảo trì cuối |
| specifications | TEXT | NULL | Thông số kỹ thuật |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |
| updated_at | TIMESTAMP | NOT NULL | Thời gian cập nhật |

**Indexes:**
- `idx_devices_serial` (serial_number)
- `idx_devices_status` (status)
- `idx_devices_type` (device_type_id)
- `idx_devices_station` (station_id)
- `idx_devices_location` (latitude, longitude)
- `idx_devices_active` (is_active)

### 3.6 Bảng DEVICE_DATA (Dữ liệu thiết bị)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| device_id | BIGINT | FK, NOT NULL | Thiết bị |
| timestamp | TIMESTAMP | NOT NULL | Thời gian đo |
| voltage | DECIMAL(10,2) | NULL | Điện áp (V) |
| current | DECIMAL(10,2) | NULL | Dòng điện (A) |
| power | DECIMAL(10,2) | NULL | Công suất (W) |
| frequency | DECIMAL(8,2) | NULL | Tần số (Hz) |
| temperature | DECIMAL(8,2) | NULL | Nhiệt độ (°C) |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_device_data_device` (device_id)
- `idx_device_data_timestamp` (timestamp)
- `idx_device_data_device_timestamp` (device_id, timestamp)

### 3.7 Bảng ALERT_LEVELS (Mức độ cảnh báo)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| name | VARCHAR(255) | UK, NOT NULL | Tên mức độ |
| description | TEXT | NULL | Mô tả |
| severity | ENUM | NOT NULL | Mức độ: LOW, MEDIUM, HIGH, CRITICAL |
| threshold | DECIMAL(10,2) | NULL | Ngưỡng cảnh báo |
| color | VARCHAR(255) | NULL | Màu hiển thị |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_alert_levels_name` (name)
- `idx_alert_levels_severity` (severity)

### 3.8 Bảng ALERT_STATUSES (Trạng thái cảnh báo)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| name | VARCHAR(255) | UK, NOT NULL | Tên trạng thái |
| description | TEXT | NULL | Mô tả |
| color | VARCHAR(255) | NULL | Màu hiển thị |
| priority | INTEGER | NOT NULL | Độ ưu tiên |
| is_active | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái hoạt động |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_alert_statuses_name` (name)
- `idx_alert_statuses_priority` (priority)

### 3.9 Bảng ALERTS (Cảnh báo)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| device_id | BIGINT | FK, NOT NULL | Thiết bị |
| alert_level_id | BIGINT | FK, NOT NULL | Mức độ cảnh báo |
| alert_status_id | BIGINT | FK, NOT NULL | Trạng thái cảnh báo |
| title | VARCHAR(255) | NOT NULL | Tiêu đề |
| description | TEXT | NULL | Mô tả |
| alert_type | ENUM | NOT NULL | Loại cảnh báo |
| threshold_value | DECIMAL(10,2) | NULL | Giá trị ngưỡng |
| actual_value | DECIMAL(10,2) | NULL | Giá trị thực tế |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |
| resolved_at | TIMESTAMP | NULL | Thời gian giải quyết |
| resolved_by_user_id | BIGINT | FK, NULL | Người giải quyết |

**Indexes:**
- `idx_alerts_device_created` (device_id, created_at)
- `idx_alerts_level` (alert_level_id)
- `idx_alerts_status` (alert_status_id)
- `idx_alerts_type` (alert_type)
- `idx_alerts_created` (created_at)

### 3.10 Bảng MAINTENANCE_RECORDS (Bảo trì)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| device_id | BIGINT | FK, NOT NULL | Thiết bị |
| type | ENUM | NOT NULL | Loại: PREVENTIVE, CORRECTIVE, EMERGENCY, INSPECTION |
| priority | ENUM | NOT NULL | Độ ưu tiên: LOW, MEDIUM, HIGH, CRITICAL |
| status | ENUM | NOT NULL | Trạng thái: SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED |
| scheduled_date | DATE | NULL | Ngày lên lịch |
| completed_date | DATE | NULL | Ngày hoàn thành |
| duration_hours | DECIMAL(8,2) | NULL | Thời gian thực hiện (giờ) |
| cost | DECIMAL(10,2) | NULL | Chi phí |
| technician | VARCHAR(255) | NULL | Kỹ thuật viên |
| supervisor_email | VARCHAR(255) | NULL | Email giám sát |
| description | TEXT | NULL | Mô tả công việc |
| work_performed | TEXT | NULL | Công việc đã thực hiện |
| issues_found | TEXT | NULL | Vấn đề phát hiện |
| parts_replaced_json | JSON | NULL | Phụ tùng thay thế |
| next_maintenance_date | DATE | NULL | Ngày bảo trì tiếp theo |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_maintenance_device` (device_id)
- `idx_maintenance_status` (status)
- `idx_maintenance_scheduled` (scheduled_date)
- `idx_maintenance_type` (type)

### 3.11 Bảng MAINTENANCE_FILES (File bảo trì)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| maintenance_record_id | BIGINT | FK, NOT NULL | Bản ghi bảo trì |
| file_name | VARCHAR(255) | NOT NULL | Tên file |
| file_path | VARCHAR(255) | NOT NULL | Đường dẫn file |
| file_type | ENUM | NOT NULL | Loại: PHOTO, VIDEO, DOCUMENT, OTHER |
| file_size | BIGINT | NULL | Kích thước file (bytes) |
| uploaded_at | TIMESTAMP | NOT NULL | Thời gian upload |

**Indexes:**
- `idx_maintenance_files_record` (maintenance_record_id)
- `idx_maintenance_files_type` (file_type)

### 3.12 Bảng LOGIN_HISTORY (Lịch sử đăng nhập)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| user_id | BIGINT | FK, NOT NULL | Người dùng |
| login_time | TIMESTAMP | NOT NULL | Thời gian đăng nhập |
| ip_address | VARCHAR(255) | NULL | Địa chỉ IP |
| location | VARCHAR(255) | NULL | Vị trí |
| device | VARCHAR(255) | NULL | Thiết bị |
| browser | VARCHAR(255) | NULL | Trình duyệt |
| success | BOOLEAN | NOT NULL | Thành công hay thất bại |

**Indexes:**
- `idx_login_history_user` (user_id)
- `idx_login_history_time` (login_time)
- `idx_login_history_success` (success)

### 3.13 Bảng USER_SESSIONS (Phiên đăng nhập)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| user_id | BIGINT | FK, NOT NULL | Người dùng |
| token | VARCHAR(500) | NOT NULL | Token JWT |
| expires_at | TIMESTAMP | NOT NULL | Thời gian hết hạn |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |

**Indexes:**
- `idx_user_sessions_user` (user_id)
- `idx_user_sessions_token` (token)
- `idx_user_sessions_expires` (expires_at)

### 3.14 Bảng SYSTEM_CONFIGS (Cấu hình hệ thống)

| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| id | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| config_key | VARCHAR(255) | UK, NOT NULL | Khóa cấu hình |
| config_value | TEXT | NULL | Giá trị cấu hình |
| description | TEXT | NULL | Mô tả |
| data_type | ENUM | NOT NULL | Kiểu dữ liệu: STRING, INTEGER, DECIMAL, BOOLEAN, JSON |
| created_at | TIMESTAMP | NOT NULL | Thời gian tạo |
| updated_at | TIMESTAMP | NOT NULL | Thời gian cập nhật |

**Indexes:**
- `idx_system_configs_key` (config_key)
- `idx_system_configs_type` (data_type)

## 4. QUAN HỆ GIỮA CÁC BẢNG

### 4.1 Quan hệ 1-N (One-to-Many)

1. **USERS → LOGIN_HISTORY**: Một user có nhiều lịch sử đăng nhập
2. **USERS → USER_SESSIONS**: Một user có nhiều phiên đăng nhập
3. **USERS → ALERTS**: Một user có thể giải quyết nhiều cảnh báo
4. **DEVICE_TYPES → DEVICES**: Một loại thiết bị có nhiều thiết bị
5. **MANUFACTURERS → DEVICES**: Một nhà sản xuất có nhiều thiết bị
6. **STATIONS → DEVICES**: Một trạm có nhiều thiết bị
7. **DEVICES → DEVICE_DATA**: Một thiết bị có nhiều dữ liệu đo
8. **DEVICES → ALERTS**: Một thiết bị có nhiều cảnh báo
9. **DEVICES → MAINTENANCE_RECORDS**: Một thiết bị có nhiều bản ghi bảo trì
10. **ALERT_LEVELS → ALERTS**: Một mức độ cảnh báo có nhiều cảnh báo
11. **ALERT_STATUSES → ALERTS**: Một trạng thái cảnh báo có nhiều cảnh báo
12. **MAINTENANCE_RECORDS → MAINTENANCE_FILES**: Một bản ghi bảo trì có nhiều file

### 4.2 Ràng buộc khóa ngoại

```sql
-- Users relationships
ALTER TABLE login_history ADD CONSTRAINT fk_login_history_user 
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE user_sessions ADD CONSTRAINT fk_user_sessions_user 
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE alerts ADD CONSTRAINT fk_alerts_resolved_by_user 
    FOREIGN KEY (resolved_by_user_id) REFERENCES users(id);

-- Device relationships
ALTER TABLE devices ADD CONSTRAINT fk_devices_device_type 
    FOREIGN KEY (device_type_id) REFERENCES device_types(id);

ALTER TABLE devices ADD CONSTRAINT fk_devices_manufacturer 
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id);

ALTER TABLE devices ADD CONSTRAINT fk_devices_station 
    FOREIGN KEY (station_id) REFERENCES stations(id);

-- Device data relationships
ALTER TABLE device_data ADD CONSTRAINT fk_device_data_device 
    FOREIGN KEY (device_id) REFERENCES devices(id);

-- Alert relationships
ALTER TABLE alerts ADD CONSTRAINT fk_alerts_device 
    FOREIGN KEY (device_id) REFERENCES devices(id);

ALTER TABLE alerts ADD CONSTRAINT fk_alerts_level 
    FOREIGN KEY (alert_level_id) REFERENCES alert_levels(id);

ALTER TABLE alerts ADD CONSTRAINT fk_alerts_status 
    FOREIGN KEY (alert_status_id) REFERENCES alert_statuses(id);

-- Maintenance relationships
ALTER TABLE maintenance_records ADD CONSTRAINT fk_maintenance_device 
    FOREIGN KEY (device_id) REFERENCES devices(id);

ALTER TABLE maintenance_files ADD CONSTRAINT fk_maintenance_files_record 
    FOREIGN KEY (maintenance_record_id) REFERENCES maintenance_records(id);
```

## 5. CẤU HÌNH DATABASE

### 5.1 Cấu hình H2 (Development)

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:powergrid
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 5.2 Cấu hình MySQL (Production)

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/powergrid?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=powergrid_user
spring.datasource.password=secure_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=false

# Connection Pool
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

### 5.3 Cấu hình PostgreSQL (Production)

```properties
# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/powergrid
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=powergrid_user
spring.datasource.password=secure_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=false

# Connection Pool
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

## 6. DỮ LIỆU MẪU

### 6.1 Dữ liệu khởi tạo

Hệ thống có sẵn `DataSeeder` để tạo dữ liệu mẫu:

- **8 tài khoản người dùng** với các vai trò khác nhau
- **8 loại thiết bị** (Transformer, Generator, Circuit Breaker, v.v.)
- **8 nhà sản xuất** (ABB, Siemens, Schneider Electric, v.v.)
- **6 trạm điện** với tọa độ thực tế
- **50 thiết bị** với thông số chi tiết
- **5 mức độ cảnh báo** (INFO, WARNING, ERROR, CRITICAL, EMERGENCY)
- **6 trạng thái cảnh báo** (NEW, ACKNOWLEDGED, IN_PROGRESS, RESOLVED, CLOSED, ESCALATED)
- **20 cảnh báo mẫu** với dữ liệu thực tế
- **8 cấu hình hệ thống** cơ bản

### 6.2 Tài khoản demo

```
Admin:    admin@powergrid.com    / admin123
Operator: operator@powergrid.com / operator123
User:     user@powergrid.com     / user123
Viewer:   viewer@powergrid.com   / viewer123
```

## 7. BẢO MẬT VÀ PHÂN QUYỀN

### 7.1 Vai trò người dùng

1. **ADMIN**: Quyền quản trị toàn bộ hệ thống
2. **OPERATOR**: Quyền vận hành, giám sát thiết bị
3. **USER**: Quyền xem và báo cáo
4. **VIEWER**: Quyền xem hạn chế

### 7.2 Mã hóa mật khẩu

- Sử dụng BCrypt để mã hóa mật khẩu
- JWT token cho xác thực API
- Session management với timeout

## 8. HIỆU SUẤT VÀ TỐI ƯU

### 8.1 Indexes quan trọng

```sql
-- Performance indexes
CREATE INDEX idx_devices_status_active ON devices(status, is_active);
CREATE INDEX idx_alerts_device_created ON alerts(device_id, created_at);
CREATE INDEX idx_device_data_timestamp ON device_data(timestamp);
CREATE INDEX idx_maintenance_scheduled ON maintenance_records(scheduled_date, status);
```

### 8.2 Partitioning (cho dữ liệu lớn)

```sql
-- Partition device_data by month
CREATE TABLE device_data_2024_01 PARTITION OF device_data
FOR VALUES FROM ('2024-01-01') TO ('2024-02-01');

CREATE TABLE device_data_2024_02 PARTITION OF device_data
FOR VALUES FROM ('2024-02-01') TO ('2024-03-01');
```

### 8.3 Backup và Recovery

```sql
-- Backup strategy
-- Daily: Full backup
-- Hourly: Incremental backup
-- Real-time: Transaction log backup
```

## 9. MIGRATION VÀ DEPLOYMENT

### 9.1 Flyway Migration

```sql
-- V1__Create_initial_schema.sql
-- V2__Add_device_locations.sql
-- V3__Add_alert_system.sql
-- V4__Add_maintenance_system.sql
```

### 9.2 Environment Configuration

```yaml
# application-dev.yml
spring:
  profiles: dev
  datasource:
    url: jdbc:h2:mem:powergrid

# application-prod.yml
spring:
  profiles: prod
  datasource:
    url: jdbc:mysql://prod-db:3306/powergrid
```

## 10. MONITORING VÀ LOGGING

### 10.1 Database Monitoring

- Connection pool metrics
- Query performance monitoring
- Deadlock detection
- Slow query logging

### 10.2 Application Logging

```properties
# Logging Configuration
logging.level.com.example.electric_api=DEBUG
logging.level.org.springframework.security=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

## 11. KẾT LUẬN

Database được thiết kế để hỗ trợ:

1. **Khả năng mở rộng**: Cấu trúc modular, dễ thêm tính năng mới
2. **Hiệu suất cao**: Indexes tối ưu, partitioning cho dữ liệu lớn
3. **Bảo mật**: Phân quyền chi tiết, mã hóa dữ liệu nhạy cảm
4. **Tính sẵn sàng**: Backup/restore, monitoring liên tục
5. **Dễ bảo trì**: Cấu trúc rõ ràng, documentation đầy đủ

Database này có thể xử lý hàng nghìn thiết bị và hàng triệu bản ghi dữ liệu với hiệu suất tốt. 