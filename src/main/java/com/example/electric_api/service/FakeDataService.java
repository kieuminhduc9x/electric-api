package com.example.electric_api.service;

import com.example.electric_api.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FakeDataService {

    private final Random random = new Random();

    // ==================== FAKE USERS ====================
    public List<User> getFakeUsers() {
        List<User> users = new ArrayList<>();
        
        Object[][] userData = {
            {"admin@powergrid.com", "System Administrator", User.Role.ADMIN, "IT Department", "System Administrator", "+84-901-234-567"},
            {"operator@powergrid.com", "Grid Operator", User.Role.OPERATOR, "Operations", "Senior Operator", "+84-901-234-568"},
            {"user@powergrid.com", "Regular User", User.Role.USER, "Engineering", "Electrical Engineer", "+84-901-234-569"},
            {"viewer@powergrid.com", "Data Viewer", User.Role.VIEWER, "Management", "Supervisor", "+84-901-234-570"},
            {"john.doe@powergrid.com", "John Doe", User.Role.USER, "Maintenance", "Technician", "+84-901-234-571"},
            {"jane.smith@powergrid.com", "Jane Smith", User.Role.OPERATOR, "Operations", "Control Room Operator", "+84-901-234-572"},
            {"mike.wilson@powergrid.com", "Mike Wilson", User.Role.USER, "Engineering", "Field Engineer", "+84-901-234-573"},
            {"sarah.johnson@powergrid.com", "Sarah Johnson", User.Role.VIEWER, "Management", "Manager", "+84-901-234-574"}
        };

        for (int i = 0; i < userData.length; i++) {
            User user = new User();
            user.setId((long) (i + 1));
            user.setEmail((String) userData[i][0]);
            user.setName((String) userData[i][1]);
            user.setRole((User.Role) userData[i][2]);
            user.setDepartment((String) userData[i][3]);
            user.setPosition((String) userData[i][4]);
            user.setPhone((String) userData[i][5]);
            user.setIsActive(true);
            user.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            users.add(user);
        }
        
        return users;
    }

    public User getFakeUserByEmail(String email) {
        return getFakeUsers().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }

    public User getFakeUserById(Long id) {
        return getFakeUsers().stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // ==================== FAKE DEVICE TYPES ====================
    public List<DeviceType> getFakeDeviceTypes() {
        List<DeviceType> deviceTypes = new ArrayList<>();
        
        Object[][] data = {
            {"Transformer", "Power transformers for voltage conversion", "🔄", "Voltage: 22kV/400V, Power: 100-2000kVA"},
            {"Generator", "Power generation equipment", "⚡", "Fuel: Diesel/Gas, Power: 50-5000kW"},
            {"Circuit Breaker", "Protection and switching equipment", "🔌", "Voltage: 1-35kV, Current: 100-4000A"},
            {"Motor", "Electric motors for various applications", "🔧", "Power: 1-1000kW, Speed: 1000-3000RPM"},
            {"Capacitor Bank", "Power factor correction equipment", "🔋", "Voltage: 400V-35kV, Capacity: 10-1000kVAR"},
            {"Relay", "Protection and control relays", "📡", "Type: Digital/Analog, Functions: Protection/Control"},
            {"Meter", "Energy and power measurement devices", "📊", "Type: Digital, Accuracy: 0.5S Class"},
            {"Switch", "Manual switching equipment", "🔄", "Voltage: 1-35kV, Current: 100-2000A"}
        };

        for (int i = 0; i < data.length; i++) {
            DeviceType deviceType = new DeviceType();
            deviceType.setId((long) (i + 1));
            deviceType.setName((String) data[i][0]);
            deviceType.setDescription((String) data[i][1]);
            deviceType.setIcon((String) data[i][2]);
            deviceType.setSpecifications((String) data[i][3]);
            deviceType.setIsActive(true);
            deviceType.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            deviceTypes.add(deviceType);
        }
        
        return deviceTypes;
    }

    // ==================== FAKE MANUFACTURERS ====================
    public List<Manufacturer> getFakeManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        
        Object[][] data = {
            {"ABB", "Leading power and automation technologies", "Switzerland", "https://www.abb.com", "Contact: +41-43-317-7111"},
            {"Siemens", "Engineering and technology solutions", "Germany", "https://www.siemens.com", "Contact: +49-89-636-00"},
            {"Schneider Electric", "Energy management and automation", "France", "https://www.schneider-electric.com", "Contact: +33-1-41-29-70-00"},
            {"General Electric", "Power generation and distribution", "USA", "https://www.ge.com", "Contact: +1-617-443-3000"},
            {"Mitsubishi Electric", "Industrial automation and power systems", "Japan", "https://www.mitsubishielectric.com", "Contact: +81-3-3218-2111"},
            {"Toshiba", "Power systems and industrial equipment", "Japan", "https://www.toshiba.com", "Contact: +81-3-3457-4511"},
            {"Eaton", "Power management solutions", "Ireland", "https://www.eaton.com", "Contact: +353-21-4805000"},
            {"Hitachi", "Power and industrial systems", "Japan", "https://www.hitachi.com", "Contact: +81-3-3258-1111"}
        };

        for (int i = 0; i < data.length; i++) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId((long) (i + 1));
            manufacturer.setName((String) data[i][0]);
            manufacturer.setDescription((String) data[i][1]);
            manufacturer.setCountry((String) data[i][2]);
            manufacturer.setWebsite((String) data[i][3]);
            manufacturer.setContactInfo((String) data[i][4]);
            manufacturer.setIsActive(true);
            manufacturer.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            manufacturers.add(manufacturer);
        }
        
        return manufacturers;
    }

    // ==================== FAKE STATIONS ====================
    public List<Station> getFakeStations() {
        List<Station> stations = new ArrayList<>();
        
        Object[][] data = {
            {"Central Power Station", "Main power distribution center", "10.7769", "106.7009", "123 Nguyen Hue, District 1, Ho Chi Minh City"},
            {"North Substation", "Northern distribution substation", "10.8231", "106.6297", "456 Le Loi, District 3, Ho Chi Minh City"},
            {"South Substation", "Southern distribution substation", "10.7467", "106.6943", "789 Hai Ba Trung, District 1, Ho Chi Minh City"},
            {"East Distribution Center", "Eastern area distribution", "10.7851", "106.7448", "321 Vo Thi Sau, District 3, Ho Chi Minh City"},
            {"West Power Hub", "Western area power hub", "10.7644", "106.6620", "654 Nam Ky Khoi Nghia, District 1, Ho Chi Minh City"},
            {"Industrial Zone Station", "Industrial area power supply", "10.8543", "106.7234", "987 Truong Chinh, Tan Binh District, Ho Chi Minh City"}
        };

        for (int i = 0; i < data.length; i++) {
            Station station = new Station();
            station.setId((long) (i + 1));
            station.setName((String) data[i][0]);
            station.setLocation((String) data[i][1]);
            station.setLatitude(new BigDecimal((String) data[i][2]));
            station.setLongitude(new BigDecimal((String) data[i][3]));
            station.setAddress((String) data[i][4]);
            station.setIsActive(true);
            station.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            stations.add(station);
        }
        
        return stations;
    }

    // ==================== FAKE DEVICES ====================
    public List<Device> getFakeDevices() {
        List<Device> devices = new ArrayList<>();
        List<DeviceType> deviceTypes = getFakeDeviceTypes();
        List<Manufacturer> manufacturers = getFakeManufacturers();
        List<Station> stations = getFakeStations();
        
        // Comprehensive device data with realistic specifications
        Object[][] deviceData = {
            // Transformers
            {"Primary Transformer T1", "Model-TF-1000", "SN100001", "ONLINE", "Rated Power: 1000kVA, Voltage: 22kV/400V, Cooling: Oil-immersed"},
            {"Secondary Transformer T2", "Model-TF-800", "SN100002", "ONLINE", "Rated Power: 800kVA, Voltage: 22kV/400V, Cooling: Oil-immersed"},
            {"Distribution Transformer DT1", "Model-TF-500", "SN100003", "ONLINE", "Rated Power: 500kVA, Voltage: 22kV/400V, Cooling: Oil-immersed"},
            {"Step-up Transformer SU1", "Model-TF-2000", "SN100004", "MAINTENANCE", "Rated Power: 2000kVA, Voltage: 11kV/22kV, Cooling: Oil-immersed"},
            {"Emergency Transformer ET1", "Model-TF-300", "SN100005", "OFFLINE", "Rated Power: 300kVA, Voltage: 22kV/400V, Cooling: Oil-immersed"},
            
            // Generators
            {"Main Generator G1", "Model-GEN-5000", "SN200001", "ONLINE", "Fuel: Diesel, Power: 5000kW, Speed: 1500RPM, Cooling: Water"},
            {"Backup Generator G2", "Model-GEN-3000", "SN200002", "ONLINE", "Fuel: Diesel, Power: 3000kW, Speed: 1500RPM, Cooling: Water"},
            {"Emergency Generator EG1", "Model-GEN-1000", "SN200003", "OFFLINE", "Fuel: Diesel, Power: 1000kW, Speed: 1500RPM, Cooling: Water"},
            {"Gas Generator GG1", "Model-GEN-2000", "SN200004", "MAINTENANCE", "Fuel: Natural Gas, Power: 2000kW, Speed: 1800RPM, Cooling: Air"},
            {"Solar Generator SG1", "Model-GEN-500", "SN200005", "ONLINE", "Fuel: Solar, Power: 500kW, Inverter: 500kVA, Battery: 1000kWh"},
            
            // Circuit Breakers
            {"Main Circuit Breaker CB1", "Model-CB-4000", "SN300001", "ONLINE", "Voltage: 22kV, Current: 4000A, Type: SF6, Operation: Spring"},
            {"Secondary Circuit Breaker CB2", "Model-CB-2000", "SN300002", "ONLINE", "Voltage: 22kV, Current: 2000A, Type: SF6, Operation: Spring"},
            {"Distribution Circuit Breaker CB3", "Model-CB-1000", "SN300003", "MAINTENANCE", "Voltage: 11kV, Current: 1000A, Type: Vacuum, Operation: Spring"},
            {"Emergency Circuit Breaker CB4", "Model-CB-500", "SN300004", "OFFLINE", "Voltage: 11kV, Current: 500A, Type: Vacuum, Operation: Manual"},
            {"High Voltage Circuit Breaker CB5", "Model-CB-6000", "SN300005", "ONLINE", "Voltage: 35kV, Current: 6000A, Type: SF6, Operation: Hydraulic"},
            
            // Motors
            {"Main Pump Motor M1", "Model-MOT-1000", "SN400001", "ONLINE", "Power: 1000kW, Speed: 1500RPM, Voltage: 400V, Type: Induction"},
            {"Cooling Fan Motor M2", "Model-MOT-100", "SN400002", "ONLINE", "Power: 100kW, Speed: 3000RPM, Voltage: 400V, Type: Induction"},
            {"Compressor Motor M3", "Model-MOT-500", "SN400003", "MAINTENANCE", "Power: 500kW, Speed: 1800RPM, Voltage: 400V, Type: Synchronous"},
            {"Conveyor Motor M4", "Model-MOT-200", "SN400004", "ONLINE", "Power: 200kW, Speed: 1200RPM, Voltage: 400V, Type: Induction"},
            {"Emergency Motor M5", "Model-MOT-50", "SN400005", "OFFLINE", "Power: 50kW, Speed: 1500RPM, Voltage: 400V, Type: Induction"},
            
            // Capacitor Banks
            {"Main Capacitor Bank C1", "Model-CAP-1000", "SN500001", "ONLINE", "Voltage: 400V, Capacity: 1000kVAR, Type: Fixed, Cooling: Air"},
            {"Power Factor Correction PFC1", "Model-CAP-500", "SN500002", "ONLINE", "Voltage: 400V, Capacity: 500kVAR, Type: Automatic, Cooling: Air"},
            {"Harmonic Filter HF1", "Model-CAP-300", "SN500003", "MAINTENANCE", "Voltage: 400V, Capacity: 300kVAR, Type: Tuned, Cooling: Air"},
            {"Reactive Compensation RC1", "Model-CAP-800", "SN500004", "ONLINE", "Voltage: 400V, Capacity: 800kVAR, Type: Switched, Cooling: Air"},
            {"Emergency Capacitor EC1", "Model-CAP-200", "SN500005", "OFFLINE", "Voltage: 400V, Capacity: 200kVAR, Type: Fixed, Cooling: Air"},
            
            // Relays
            {"Protection Relay R1", "Model-REL-100", "SN600001", "ONLINE", "Type: Digital, Functions: Overcurrent, Earth Fault, Voltage Protection"},
            {"Control Relay R2", "Model-REL-50", "SN600002", "ONLINE", "Type: Digital, Functions: Control, Monitoring, Communication"},
            {"Distance Relay R3", "Model-REL-200", "SN600003", "MAINTENANCE", "Type: Digital, Functions: Distance Protection, Fault Location"},
            {"Differential Relay R4", "Model-REL-150", "SN600004", "ONLINE", "Type: Digital, Functions: Differential Protection, Transformer Protection"},
            {"Backup Relay R5", "Model-REL-75", "SN600005", "OFFLINE", "Type: Electromechanical, Functions: Backup Protection, Time Delay"},
            
            // Meters
            {"Energy Meter EM1", "Model-MET-100", "SN700001", "ONLINE", "Type: Digital, Accuracy: 0.5S Class, Communication: Modbus RTU"},
            {"Power Meter PM1", "Model-MET-200", "SN700002", "ONLINE", "Type: Digital, Accuracy: 0.2S Class, Communication: Modbus TCP"},
            {"Power Quality Meter PQM1", "Model-MET-300", "SN700003", "MAINTENANCE", "Type: Digital, Accuracy: 0.1S Class, Functions: Harmonics, Flicker"},
            {"Revenue Meter RM1", "Model-MET-400", "SN700004", "ONLINE", "Type: Digital, Accuracy: 0.2S Class, Certified: Revenue Grade"},
            {"Test Meter TM1", "Model-MET-500", "SN700005", "OFFLINE", "Type: Portable, Accuracy: 0.5S Class, Functions: Testing, Calibration"},
            
            // Switches
            {"Main Switch S1", "Model-SW-4000", "SN800001", "ONLINE", "Voltage: 22kV, Current: 4000A, Type: Load Break, Operation: Motor"},
            {"Isolation Switch S2", "Model-SW-2000", "SN800002", "ONLINE", "Voltage: 22kV, Current: 2000A, Type: Isolator, Operation: Manual"},
            {"Load Break Switch LBS1", "Model-SW-1000", "SN800003", "MAINTENANCE", "Voltage: 11kV, Current: 1000A, Type: Load Break, Operation: Manual"},
            {"Disconnector DS1", "Model-SW-500", "SN800004", "OFFLINE", "Voltage: 11kV, Current: 500A, Type: Disconnector, Operation: Manual"},
            {"Emergency Switch ES1", "Model-SW-300", "SN800005", "ONLINE", "Voltage: 400V, Current: 300A, Type: Emergency Stop, Operation: Manual"},
            
            // Current Transformers
            {"Main Current Transformer CT1", "Model-CT-4000", "SN900001", "ONLINE", "Ratio: 4000/5A, Accuracy: 0.5S Class, Burden: 15VA"},
            {"Secondary Current Transformer CT2", "Model-CT-2000", "SN900002", "ONLINE", "Ratio: 2000/5A, Accuracy: 0.5S Class, Burden: 15VA"},
            {"Protection Current Transformer CT3", "Model-CT-1000", "SN900003", "MAINTENANCE", "Ratio: 1000/5A, Accuracy: 5P Class, Burden: 30VA"},
            {"Measurement Current Transformer CT4", "Model-CT-500", "SN900004", "ONLINE", "Ratio: 500/5A, Accuracy: 0.2S Class, Burden: 10VA"},
            {"Test Current Transformer CT5", "Model-CT-100", "SN900005", "OFFLINE", "Ratio: 100/5A, Accuracy: 0.1S Class, Burden: 5VA"},
            
            // Voltage Transformers
            {"Main Voltage Transformer VT1", "Model-VT-22000", "SN100001", "ONLINE", "Ratio: 22000/110V, Accuracy: 0.5S Class, Burden: 50VA"},
            {"Secondary Voltage Transformer VT2", "Model-VT-11000", "SN100002", "ONLINE", "Ratio: 11000/110V, Accuracy: 0.5S Class, Burden: 50VA"},
            {"Protection Voltage Transformer VT3", "Model-VT-11000", "SN100003", "MAINTENANCE", "Ratio: 11000/110V, Accuracy: 3P Class, Burden: 100VA"},
            {"Measurement Voltage Transformer VT4", "Model-VT-400", "SN100004", "ONLINE", "Ratio: 400/110V, Accuracy: 0.2S Class, Burden: 25VA"},
            {"Test Voltage Transformer VT5", "Model-VT-110", "SN100005", "OFFLINE", "Ratio: 110/110V, Accuracy: 0.1S Class, Burden: 10VA"},
            
            // Surge Arresters
            {"Main Surge Arrester SA1", "Model-SA-22000", "SN110001", "ONLINE", "Voltage: 22kV, Type: Metal Oxide, Protection Level: 60kV"},
            {"Secondary Surge Arrester SA2", "Model-SA-11000", "SN110002", "ONLINE", "Voltage: 11kV, Type: Metal Oxide, Protection Level: 30kV"},
            {"Distribution Surge Arrester SA3", "Model-SA-400", "SN110003", "MAINTENANCE", "Voltage: 400V, Type: Metal Oxide, Protection Level: 2.5kV"},
            {"Communication Surge Arrester SA4", "Model-SA-24", "SN110004", "ONLINE", "Voltage: 24V, Type: Gas Discharge, Protection Level: 100V"},
            {"Emergency Surge Arrester SA5", "Model-SA-220", "SN110005", "OFFLINE", "Voltage: 220V, Type: Metal Oxide, Protection Level: 1.5kV"}
        };

        Device.DeviceStatus[] statuses = Device.DeviceStatus.values();

        for (int i = 0; i < deviceData.length; i++) {
            Device device = new Device();
            device.setId((long) (i + 1));
            device.setDeviceType(deviceTypes.get(random.nextInt(deviceTypes.size())));
            device.setManufacturer(manufacturers.get(random.nextInt(manufacturers.size())));
            device.setStation(stations.get(random.nextInt(stations.size())));
            device.setName((String) deviceData[i][0]);
            device.setModel((String) deviceData[i][1]);
            device.setSerialNumber((String) deviceData[i][2]);
            device.setStatus(Device.DeviceStatus.valueOf((String) deviceData[i][3]));
            device.setInstallationDate(LocalDate.now().minusDays(random.nextInt(1000)));
            device.setLastMaintenance(LocalDate.now().minusDays(random.nextInt(90)));
            device.setSpecifications((String) deviceData[i][4]);
            device.setIsActive(true);
            device.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            devices.add(device);
        }
        
        return devices;
    }

    public Device getFakeDeviceById(Long id) {
        return getFakeDevices().stream()
            .filter(device -> device.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // ==================== FAKE ALERT LEVELS ====================
    public List<AlertLevel> getFakeAlertLevels() {
        List<AlertLevel> alertLevels = new ArrayList<>();
        
        Object[][] data = {
            {"LOW", "Low priority alert", "LOW", "10", "#17a2b8"},
            {"MEDIUM", "Medium priority alert", "MEDIUM", "25", "#ffc107"},
            {"HIGH", "High priority alert", "HIGH", "50", "#fd7e14"},
            {"CRITICAL", "Critical level alert", "CRITICAL", "75", "#dc3545"}
        };

        for (int i = 0; i < data.length; i++) {
            AlertLevel alertLevel = new AlertLevel();
            alertLevel.setId((long) (i + 1));
            alertLevel.setName((String) data[i][0]);
            alertLevel.setDescription((String) data[i][1]);
            alertLevel.setSeverity(AlertLevel.Severity.valueOf((String) data[i][2]));
            alertLevel.setThreshold(new BigDecimal((String) data[i][3]));
            alertLevel.setColor((String) data[i][4]);
            alertLevel.setIsActive(true);
            alertLevel.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            alertLevels.add(alertLevel);
        }
        
        return alertLevels;
    }

    // ==================== FAKE ALERT STATUSES ====================
    public List<AlertStatus> getFakeAlertStatuses() {
        List<AlertStatus> alertStatuses = new ArrayList<>();
        
        Object[][] data = {
            {"NEW", "Newly created alert", "#007bff", "1"},
            {"ACKNOWLEDGED", "Alert has been acknowledged", "#6c757d", "2"},
            {"IN_PROGRESS", "Alert is being investigated", "#ffc107", "3"},
            {"RESOLVED", "Alert has been resolved", "#28a745", "4"},
            {"CLOSED", "Alert has been closed", "#6f42c1", "5"},
            {"ESCALATED", "Alert has been escalated", "#dc3545", "6"}
        };

        for (int i = 0; i < data.length; i++) {
            AlertStatus alertStatus = new AlertStatus();
            alertStatus.setId((long) (i + 1));
            alertStatus.setName((String) data[i][0]);
            alertStatus.setDescription((String) data[i][1]);
            alertStatus.setColor((String) data[i][2]);
            alertStatus.setPriority(Integer.parseInt((String) data[i][3]));
            alertStatus.setIsActive(true);
            alertStatus.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            alertStatuses.add(alertStatus);
        }
        
        return alertStatuses;
    }

    // ==================== FAKE ALERTS ====================
    public List<Alert> getFakeAlerts() {
        List<Alert> alerts = new ArrayList<>();
        List<Device> devices = getFakeDevices();
        List<AlertLevel> alertLevels = getFakeAlertLevels();
        List<AlertStatus> alertStatuses = getFakeAlertStatuses();
        List<User> users = getFakeUsers();
        
        String[] alertTitles = {
            "High Temperature Warning", "Voltage Deviation Alert", "Current Overload", "Communication Failure",
            "Equipment Malfunction", "Power Quality Issue", "Maintenance Required", "Security Breach",
            "System Overload", "Frequency Deviation", "Phase Imbalance", "Ground Fault",
            "Arc Flash Warning", "Insulation Failure", "Cooling System Alert", "Battery Backup Low"
        };

        Alert.AlertType[] alertTypes = Alert.AlertType.values();

        for (int i = 0; i < 25; i++) {
            Alert alert = new Alert();
            alert.setId((long) (i + 1));
            alert.setDevice(devices.get(random.nextInt(devices.size())));
            alert.setAlertLevel(alertLevels.get(random.nextInt(alertLevels.size())));
            alert.setAlertStatus(alertStatuses.get(random.nextInt(alertStatuses.size())));
            alert.setTitle(alertTitles[random.nextInt(alertTitles.length)]);
            alert.setDescription("Alert generated by monitoring system. Device parameter exceeded threshold limits.");
            alert.setAlertType(alertTypes[random.nextInt(alertTypes.length)]);
            alert.setThresholdValue(new BigDecimal(50 + random.nextInt(50)));
            alert.setActualValue(new BigDecimal(60 + random.nextInt(100)));
            alert.setCreatedAt(LocalDateTime.now().minusHours(random.nextInt(168))); // Last week

            // Some alerts are resolved
            if (random.nextBoolean()) {
                alert.setResolvedAt(alert.getCreatedAt().plusHours(1 + random.nextInt(24)));
                alert.setResolvedByUser(users.get(random.nextInt(users.size())));
            }

            alerts.add(alert);
        }
        
        return alerts;
    }

    // ==================== FAKE DEVICE DATA ====================
    public List<Map<String, Object>> getFakeDeviceData(Long deviceId, int count) {
        List<Map<String, Object>> data = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            Map<String, Object> reading = new HashMap<>();
            reading.put("id", i + 1);
            reading.put("deviceId", deviceId);
            reading.put("timestamp", LocalDateTime.now().minusMinutes(i * 5));
            reading.put("voltage", BigDecimal.valueOf(220 + random.nextGaussian() * 10));
            reading.put("current", BigDecimal.valueOf(30 + random.nextGaussian() * 5));
            reading.put("power", BigDecimal.valueOf(6600 + random.nextGaussian() * 500));
            reading.put("frequency", BigDecimal.valueOf(50 + random.nextGaussian() * 0.5));
            reading.put("temperature", BigDecimal.valueOf(25 + random.nextGaussian() * 10));
            reading.put("humidity", BigDecimal.valueOf(60 + random.nextGaussian() * 20));
            reading.put("vibration", BigDecimal.valueOf(random.nextGaussian() * 2));
            data.add(reading);
        }
        
        return data;
    }

    // ==================== UTILITY METHODS ====================
    public Map<String, Object> getFakeStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Device statistics
        Map<String, Long> deviceStats = new HashMap<>();
        deviceStats.put("online", 15L);
        deviceStats.put("offline", 5L);
        deviceStats.put("maintenance", 3L);
        deviceStats.put("error", 2L);
        
        // Alert statistics
        Map<String, Long> alertStats = new HashMap<>();
        alertStats.put("total", 25L);
        alertStats.put("resolved", 15L);
        alertStats.put("critical", 3L);
        alertStats.put("unresolved", 10L);
        
        // User statistics
        Map<String, Long> userStats = new HashMap<>();
        userStats.put("total", 8L);
        userStats.put("active", 8L);
        userStats.put("admin", 1L);
        userStats.put("operator", 2L);
        userStats.put("user", 3L);
        userStats.put("viewer", 2L);
        
        stats.put("devices", deviceStats);
        stats.put("alerts", alertStats);
        stats.put("users", userStats);
        stats.put("lastUpdated", LocalDateTime.now());
        stats.put("systemStatus", "OPERATIONAL");
        stats.put("dataSource", "FAKE_DATA");
        
        return stats;
    }

    // Search methods
    public List<User> searchFakeUsers(String query) {
        return getFakeUsers().stream()
            .filter(user -> user.getName().toLowerCase().contains(query.toLowerCase()) ||
                           user.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                           user.getDepartment().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Device> searchFakeDevices(String query) {
        return getFakeDevices().stream()
            .filter(device -> device.getName().toLowerCase().contains(query.toLowerCase()) ||
                            device.getModel().toLowerCase().contains(query.toLowerCase()) ||
                            device.getSerialNumber().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Alert> filterFakeAlerts(String level, String status, String type, Long deviceId, Boolean resolved) {
        return getFakeAlerts().stream()
            .filter(alert -> level == null || alert.getAlertLevel().getName().equalsIgnoreCase(level))
            .filter(alert -> status == null || alert.getAlertStatus().getName().equalsIgnoreCase(status))
            .filter(alert -> type == null || alert.getAlertType().name().equalsIgnoreCase(type))
            .filter(alert -> deviceId == null || alert.getDevice().getId().equals(deviceId))
            .filter(alert -> resolved == null || (resolved && alert.getResolvedAt() != null) || (!resolved && alert.getResolvedAt() == null))
            .collect(Collectors.toList());
    }

    // ==================== DASHBOARD OVERVIEW ====================
    public Map<String, Object> getDashboardOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        // Get all fake devices for calculations
        List<Device> allDevices = getFakeDevices();
        List<Alert> allAlerts = getFakeAlerts();
        
        // Calculate device statistics
        int totalDevices = allDevices.size();
        int onlineDevices = (int) allDevices.stream()
            .filter(device -> device.getStatus() == Device.DeviceStatus.ONLINE)
            .count();
        int offlineDevices = (int) allDevices.stream()
            .filter(device -> device.getStatus() == Device.DeviceStatus.OFFLINE)
            .count();
        int maintenanceDevices = (int) allDevices.stream()
            .filter(device -> device.getStatus() == Device.DeviceStatus.MAINTENANCE)
            .count();
        int errorDevices = (int) allDevices.stream()
            .filter(device -> device.getStatus() == Device.DeviceStatus.ERROR)
            .count();
        int warningDevices = (int) allDevices.stream()
            .filter(device -> device.getStatus() == Device.DeviceStatus.WARNING)
            .count();
        
        // Calculate power statistics
        double totalPower = allDevices.stream()
            .mapToDouble(device -> {
                // Simulate power based on device type
                String deviceName = device.getName().toLowerCase();
                if (deviceName.contains("transformer")) return 1000 + random.nextInt(2000);
                if (deviceName.contains("generator")) return 500 + random.nextInt(1000);
                if (deviceName.contains("motor")) return 100 + random.nextInt(500);
                return 50 + random.nextInt(200);
            })
            .sum();
        
        double totalVoltage = 220.0 + (random.nextDouble() - 0.5) * 20; // 210-230V range
        
        // Calculate active alerts
        int activeAlerts = (int) allAlerts.stream()
            .filter(alert -> alert.getAlertStatus() != null && "ACTIVE".equals(alert.getAlertStatus().getName()))
            .count();
        
        // Power consumption data (weekly simulation)
        List<Map<String, Object>> powerConsumption = Arrays.asList(
            createConsumptionData("T2", 1200 + random.nextInt(200)),
            createConsumptionData("T3", 1100 + random.nextInt(200)),
            createConsumptionData("T4", 1350 + random.nextInt(200)),
            createConsumptionData("T5", 1280 + random.nextInt(200)),
            createConsumptionData("T6", 1450 + random.nextInt(200)),
            createConsumptionData("T7", 980 + random.nextInt(200)),
            createConsumptionData("CN", 890 + random.nextInt(200))
        );
        
        // Voltage distribution data
        List<Map<String, Object>> voltageDistribution = Arrays.asList(
            createVoltageRange("220-230V", 45, 65),
            createVoltageRange("210-220V", 15, 22),
            createVoltageRange("200-210V", 8, 11),
            createVoltageRange(">230V", 1, 1),
            createVoltageRange("<200V", 1, 1)
        );
        
        // Power trend data (24-hour simulation)
        List<Map<String, Object>> powerTrend = new ArrayList<>();
        String[] timeSlots = {"00:00", "04:00", "08:00", "12:00", "16:00", "20:00"};
        double[] basePower = {2200, 1744, 3375, 4440, 3942, 2652};
        double[] baseVoltage = {220, 218, 222, 225, 223, 221};
        double[] baseCurrent = {30, 25, 35, 40, 38, 28};
        
        for (int i = 0; i < timeSlots.length; i++) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("time", timeSlots[i]);
            dataPoint.put("voltage", Math.round((baseVoltage[i] + random.nextDouble() * 4 - 2) * 10.0) / 10.0);
            dataPoint.put("current", Math.round((baseCurrent[i] + random.nextDouble() * 6 - 3) * 10.0) / 10.0);
            dataPoint.put("power", Math.round((basePower[i] + random.nextInt(200) - 100) * 10.0) / 10.0);
            powerTrend.add(dataPoint);
        }
        
        // Build overview response
        overview.put("totalDevices", totalDevices);
        overview.put("onlineDevices", onlineDevices);
        overview.put("offlineDevices", offlineDevices);
        overview.put("maintenanceDevices", maintenanceDevices);
        overview.put("errorDevices", errorDevices);
        overview.put("warningDevices", warningDevices);
        overview.put("totalPower", Math.round(totalPower * 10.0) / 10.0);
        overview.put("totalVoltage", Math.round(totalVoltage * 10.0) / 10.0);
        overview.put("alerts", activeAlerts);
        overview.put("powerConsumption", powerConsumption);
        overview.put("voltageDistribution", voltageDistribution);
        overview.put("powerTrend", powerTrend);
        
        return overview;
    }
    
    private Map<String, Object> createVoltageRange(String range, int count, int percentage) {
        Map<String, Object> voltageRange = new HashMap<>();
        voltageRange.put("range", range);
        voltageRange.put("count", count);
        voltageRange.put("percentage", percentage);
        return voltageRange;
    }

    private Map<String, Object> createConsumptionData(String period, int consumption) {
        Map<String, Object> data = new HashMap<>();
        data.put("period", period);
        data.put("consumption", consumption);
        return data;
    }
    
    public Map<String, Object> getFakeAlertsSummary() {
        List<Alert> allAlerts = getFakeAlerts();
        
        Map<String, Object> summary = new HashMap<>();
        
        // Count alerts by level
        long criticalAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertLevel() != null && "CRITICAL".equals(alert.getAlertLevel().getName()))
            .count();
        long highAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertLevel() != null && "HIGH".equals(alert.getAlertLevel().getName()))
            .count();
        long mediumAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertLevel() != null && "MEDIUM".equals(alert.getAlertLevel().getName()))
            .count();
        long lowAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertLevel() != null && "LOW".equals(alert.getAlertLevel().getName()))
            .count();
        
        // Count alerts by status
        long activeAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertStatus() != null && "ACTIVE".equals(alert.getAlertStatus().getName()))
            .count();
        long resolvedAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertStatus() != null && "RESOLVED".equals(alert.getAlertStatus().getName()))
            .count();
        long acknowledgedAlerts = allAlerts.stream()
            .filter(alert -> alert.getAlertStatus() != null && "ACKNOWLEDGED".equals(alert.getAlertStatus().getName()))
            .count();
        
        summary.put("totalAlerts", allAlerts.size());
        summary.put("criticalAlerts", criticalAlerts);
        summary.put("highAlerts", highAlerts);
        summary.put("mediumAlerts", mediumAlerts);
        summary.put("lowAlerts", lowAlerts);
        summary.put("activeAlerts", activeAlerts);
        summary.put("resolvedAlerts", resolvedAlerts);
        summary.put("acknowledgedAlerts", acknowledgedAlerts);
        
        return summary;
    }
} 