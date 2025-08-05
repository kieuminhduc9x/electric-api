package com.example.electric_api.config;

import com.example.electric_api.entity.*;
import com.example.electric_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Value("${app.data-seeding.enabled:true}")
    private boolean dataSeedingEnabled;

    @Autowired private UserRepository userRepository;
    @Autowired private DeviceTypeRepository deviceTypeRepository;
    @Autowired private ManufacturerRepository manufacturerRepository;
    @Autowired private StationRepository stationRepository;
    @Autowired private DeviceRepository deviceRepository;
    @Autowired private AlertLevelRepository alertLevelRepository;
    @Autowired private AlertStatusRepository alertStatusRepository;
    @Autowired private AlertRepository alertRepository;
    @Autowired private SystemConfigRepository systemConfigRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!dataSeedingEnabled) {
            System.out.println("🚫 Data seeding is disabled");
            return;
        }

        try {
            // Kiểm tra nếu đã có data
            if (userRepository.count() > 0) {
                System.out.println("✅ Data already exists, skipping initialization");
                return;
            }

            System.out.println("🚀 Initializing comprehensive fake data...");

            // Seed data theo thứ tự dependency
            seedUsers();
            seedDeviceTypes();
            seedManufacturers();
            seedStations();
            seedDevices();
            seedAlertLevels();
            seedAlertStatuses();
            seedAlerts();
            seedSystemConfigs();

            System.out.println("✅ Complete fake data initialized successfully!");
            printSummary();

        } catch (Exception e) {
            System.err.println("❌ Error during data seeding: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seedUsers() {
        System.out.println("👥 Creating demo users...");

        List<Object[]> userData = Arrays.asList(
            new Object[]{"admin@powergrid.com", "admin123", "System Administrator", User.Role.ADMIN, "IT Department", "System Administrator", "+84-901-234-567"},
            new Object[]{"operator@powergrid.com", "operator123", "Grid Operator", User.Role.OPERATOR, "Operations", "Senior Operator", "+84-901-234-568"},
            new Object[]{"user@powergrid.com", "user123", "Regular User", User.Role.USER, "Engineering", "Electrical Engineer", "+84-901-234-569"},
            new Object[]{"viewer@powergrid.com", "viewer123", "Data Viewer", User.Role.VIEWER, "Management", "Supervisor", "+84-901-234-570"},
            new Object[]{"john.doe@powergrid.com", "password123", "John Doe", User.Role.USER, "Maintenance", "Technician", "+84-901-234-571"},
            new Object[]{"jane.smith@powergrid.com", "password123", "Jane Smith", User.Role.OPERATOR, "Operations", "Control Room Operator", "+84-901-234-572"},
            new Object[]{"mike.wilson@powergrid.com", "password123", "Mike Wilson", User.Role.USER, "Engineering", "Field Engineer", "+84-901-234-573"},
            new Object[]{"sarah.johnson@powergrid.com", "password123", "Sarah Johnson", User.Role.VIEWER, "Management", "Manager", "+84-901-234-574"}
        );

        for (Object[] data : userData) {
            User user = new User();
            user.setEmail((String) data[0]);
            user.setPassword(passwordEncoder.encode((String) data[1]));
            user.setName((String) data[2]);
            user.setRole((User.Role) data[3]);
            user.setDepartment((String) data[4]);
            user.setPosition((String) data[5]);
            user.setPhone((String) data[6]);
            user.setIsActive(true);
            userRepository.save(user);
        }

        System.out.println("✅ Created " + userData.size() + " demo users");
    }

    private void seedDeviceTypes() {
        System.out.println("⚙️ Creating device types...");

        List<Object[]> deviceTypeData = Arrays.asList(
            new Object[]{"Transformer", "Power transformers for voltage conversion", "🔄", "Voltage: 22kV/400V, Power: 100-2000kVA"},
            new Object[]{"Generator", "Power generation equipment", "⚡", "Fuel: Diesel/Gas, Power: 50-5000kW"},
            new Object[]{"Circuit Breaker", "Protection and switching equipment", "🔌", "Voltage: 1-35kV, Current: 100-4000A"},
            new Object[]{"Motor", "Electric motors for various applications", "🔧", "Power: 1-1000kW, Speed: 1000-3000RPM"},
            new Object[]{"Capacitor Bank", "Power factor correction equipment", "🔋", "Voltage: 400V-35kV, Capacity: 10-1000kVAR"},
            new Object[]{"Relay", "Protection and control relays", "📡", "Type: Digital/Analog, Functions: Protection/Control"},
            new Object[]{"Meter", "Energy and power measurement devices", "📊", "Type: Digital, Accuracy: 0.5S Class"},
            new Object[]{"Switch", "Manual switching equipment", "🔄", "Voltage: 1-35kV, Current: 100-2000A"}
        );

        for (Object[] data : deviceTypeData) {
            DeviceType deviceType = new DeviceType();
            deviceType.setName((String) data[0]);
            deviceType.setDescription((String) data[1]);
            deviceType.setIcon((String) data[2]);
            deviceType.setSpecifications((String) data[3]);
            deviceType.setIsActive(true);
            deviceTypeRepository.save(deviceType);
        }

        System.out.println("✅ Created " + deviceTypeData.size() + " device types");
    }

    private void seedManufacturers() {
        System.out.println("🏭 Creating manufacturers...");

        List<Object[]> manufacturerData = Arrays.asList(
            new Object[]{"ABB", "Leading power and automation technologies", "Switzerland", "https://www.abb.com", "Contact: +41-43-317-7111"},
            new Object[]{"Siemens", "Engineering and technology solutions", "Germany", "https://www.siemens.com", "Contact: +49-89-636-00"},
            new Object[]{"Schneider Electric", "Energy management and automation", "France", "https://www.schneider-electric.com", "Contact: +33-1-41-29-70-00"},
            new Object[]{"General Electric", "Power generation and distribution", "USA", "https://www.ge.com", "Contact: +1-617-443-3000"},
            new Object[]{"Mitsubishi Electric", "Industrial automation and power systems", "Japan", "https://www.mitsubishielectric.com", "Contact: +81-3-3218-2111"},
            new Object[]{"Toshiba", "Power systems and industrial equipment", "Japan", "https://www.toshiba.com", "Contact: +81-3-3457-4511"},
            new Object[]{"Eaton", "Power management solutions", "Ireland", "https://www.eaton.com", "Contact: +353-21-4805000"},
            new Object[]{"Hitachi", "Power and industrial systems", "Japan", "https://www.hitachi.com", "Contact: +81-3-3258-1111"}
        );

        for (Object[] data : manufacturerData) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName((String) data[0]);
            manufacturer.setDescription((String) data[1]);
            manufacturer.setCountry((String) data[2]);
            manufacturer.setWebsite((String) data[3]);
            manufacturer.setContactInfo((String) data[4]);
            manufacturer.setIsActive(true);
            manufacturerRepository.save(manufacturer);
        }

        System.out.println("✅ Created " + manufacturerData.size() + " manufacturers");
    }

    private void seedStations() {
        System.out.println("🏢 Creating power stations...");

        List<Object[]> stationData = Arrays.asList(
            new Object[]{"Central Power Station", "Main power distribution center", new BigDecimal("10.7769"), new BigDecimal("106.7009"), "123 Nguyen Hue, District 1, Ho Chi Minh City"},
            new Object[]{"North Substation", "Northern distribution substation", new BigDecimal("10.8231"), new BigDecimal("106.6297"), "456 Le Loi, District 3, Ho Chi Minh City"},
            new Object[]{"South Substation", "Southern distribution substation", new BigDecimal("10.7467"), new BigDecimal("106.6943"), "789 Hai Ba Trung, District 1, Ho Chi Minh City"},
            new Object[]{"East Distribution Center", "Eastern area distribution", new BigDecimal("10.7851"), new BigDecimal("106.7448"), "321 Vo Thi Sau, District 3, Ho Chi Minh City"},
            new Object[]{"West Power Hub", "Western area power hub", new BigDecimal("10.7644"), new BigDecimal("106.6620"), "654 Nam Ky Khoi Nghia, District 1, Ho Chi Minh City"},
            new Object[]{"Industrial Zone Station", "Industrial area power supply", new BigDecimal("10.8543"), new BigDecimal("106.7234"), "987 Truong Chinh, Tan Binh District, Ho Chi Minh City"}
        );

        for (Object[] data : stationData) {
            Station station = new Station();
            station.setName((String) data[0]);
            station.setLocation((String) data[1]);
            station.setLatitude((BigDecimal) data[2]);
            station.setLongitude((BigDecimal) data[3]);
            station.setAddress((String) data[4]);
            station.setIsActive(true);
            stationRepository.save(station);
        }

        System.out.println("✅ Created " + stationData.size() + " power stations");
    }

    private void seedDevices() {
        System.out.println("⚡ Creating devices...");

        List<DeviceType> deviceTypes = deviceTypeRepository.findAll();
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        List<Station> stations = stationRepository.findAll();

        if (deviceTypes.isEmpty() || manufacturers.isEmpty() || stations.isEmpty()) {
            System.out.println("⚠️ Cannot create devices - missing dependencies");
            return;
        }

        Random random = new Random();
        
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
            deviceRepository.save(device);
        }

        System.out.println("✅ Created " + deviceData.length + " comprehensive devices");
    }

    private void seedAlertLevels() {
        System.out.println("🚨 Creating alert levels...");

        List<Object[]> alertLevelData = Arrays.asList(
            new Object[]{"INFO", "Information level alert", AlertLevel.Severity.LOW, new BigDecimal("10"), "#17a2b8"},
            new Object[]{"WARNING", "Warning level alert", AlertLevel.Severity.MEDIUM, new BigDecimal("25"), "#ffc107"},
            new Object[]{"ERROR", "Error level alert", AlertLevel.Severity.HIGH, new BigDecimal("50"), "#fd7e14"},
            new Object[]{"CRITICAL", "Critical level alert", AlertLevel.Severity.CRITICAL, new BigDecimal("75"), "#dc3545"},
            new Object[]{"EMERGENCY", "Emergency level alert", AlertLevel.Severity.CRITICAL, new BigDecimal("90"), "#6f42c1"}
        );

        for (Object[] data : alertLevelData) {
            AlertLevel alertLevel = new AlertLevel();
            alertLevel.setName((String) data[0]);
            alertLevel.setDescription((String) data[1]);
            alertLevel.setSeverity((AlertLevel.Severity) data[2]);
            alertLevel.setThreshold((BigDecimal) data[3]);
            alertLevel.setColor((String) data[4]);
            alertLevel.setIsActive(true);
            alertLevelRepository.save(alertLevel);
        }

        System.out.println("✅ Created " + alertLevelData.size() + " alert levels");
    }

    private void seedAlertStatuses() {
        System.out.println("📊 Creating alert statuses...");

        List<Object[]> alertStatusData = Arrays.asList(
            new Object[]{"NEW", "Newly created alert", "#007bff", 1},
            new Object[]{"ACKNOWLEDGED", "Alert has been acknowledged", "#6c757d", 2},
            new Object[]{"IN_PROGRESS", "Alert is being investigated", "#ffc107", 3},
            new Object[]{"RESOLVED", "Alert has been resolved", "#28a745", 4},
            new Object[]{"CLOSED", "Alert has been closed", "#6f42c1", 5},
            new Object[]{"ESCALATED", "Alert has been escalated", "#dc3545", 6}
        );

        for (Object[] data : alertStatusData) {
            AlertStatus alertStatus = new AlertStatus();
            alertStatus.setName((String) data[0]);
            alertStatus.setDescription((String) data[1]);
            alertStatus.setColor((String) data[2]);
            alertStatus.setPriority((Integer) data[3]);
            alertStatus.setIsActive(true);
            alertStatusRepository.save(alertStatus);
        }

        System.out.println("✅ Created " + alertStatusData.size() + " alert statuses");
    }

    private void seedAlerts() {
        System.out.println("🚨 Creating sample alerts...");

        List<Device> devices = deviceRepository.findAll();
        List<AlertLevel> alertLevels = alertLevelRepository.findAll();
        List<AlertStatus> alertStatuses = alertStatusRepository.findAll();
        List<User> users = userRepository.findAll();

        if (devices.isEmpty() || alertLevels.isEmpty() || alertStatuses.isEmpty()) {
            System.out.println("⚠️ Cannot create alerts - missing dependencies");
            return;
        }

        Random random = new Random();
        String[] alertTitles = {
            "High Temperature Warning", "Voltage Deviation Alert", "Current Overload", "Communication Failure",
            "Equipment Malfunction", "Power Quality Issue", "Maintenance Required", "Security Breach",
            "System Overload", "Frequency Deviation", "Phase Imbalance", "Ground Fault",
            "Arc Flash Warning", "Insulation Failure", "Cooling System Alert", "Battery Backup Low"
        };

        Alert.AlertType[] alertTypes = Alert.AlertType.values();

        for (int i = 0; i < 20; i++) {
            Alert alert = new Alert();
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
                if (!users.isEmpty()) {
                    alert.setResolvedByUser(users.get(random.nextInt(users.size())));
                }
            }

            alertRepository.save(alert);
        }

        System.out.println("✅ Created 20 sample alerts");
    }

    private void seedSystemConfigs() {
        System.out.println("⚙️ Creating system configurations...");

        List<Object[]> configData = Arrays.asList(
            new Object[]{"system.name", "Power Grid Management System", "System display name", SystemConfig.DataType.STRING},
            new Object[]{"system.version", "1.0.0", "System version", SystemConfig.DataType.STRING},
            new Object[]{"monitoring.interval", "30", "Monitoring interval in seconds", SystemConfig.DataType.INTEGER},
            new Object[]{"alert.retention.days", "365", "Alert retention period in days", SystemConfig.DataType.INTEGER},
            new Object[]{"maintenance.reminder.enabled", "true", "Enable maintenance reminders", SystemConfig.DataType.BOOLEAN},
            new Object[]{"email.notifications.enabled", "false", "Enable email notifications", SystemConfig.DataType.BOOLEAN},
            new Object[]{"data.backup.enabled", "true", "Enable automatic data backup", SystemConfig.DataType.BOOLEAN},
            new Object[]{"security.session.timeout", "3600", "Session timeout in seconds", SystemConfig.DataType.INTEGER}
        );

        for (Object[] data : configData) {
            SystemConfig config = new SystemConfig();
            config.setConfigKey((String) data[0]);
            config.setConfigValue((String) data[1]);
            config.setDescription((String) data[2]);
            config.setDataType((SystemConfig.DataType) data[3]);
            systemConfigRepository.save(config);
        }

        System.out.println("✅ Created " + configData.size() + " system configurations");
    }

    private void printSummary() {
        System.out.println("\n📊 === DATA SEEDING SUMMARY ===");
        System.out.println("👥 Users: " + userRepository.count());
        System.out.println("⚙️ Device Types: " + deviceTypeRepository.count());
        System.out.println("🏭 Manufacturers: " + manufacturerRepository.count());
        System.out.println("🏢 Stations: " + stationRepository.count());
        System.out.println("⚡ Devices: " + deviceRepository.count());
        System.out.println("🚨 Alert Levels: " + alertLevelRepository.count());
        System.out.println("📊 Alert Statuses: " + alertStatusRepository.count());
        System.out.println("🚨 Alerts: " + alertRepository.count());
        System.out.println("⚙️ System Configs: " + systemConfigRepository.count());
        System.out.println("\n🔑 === DEMO ACCOUNTS ===");
        System.out.println("Admin:    admin@powergrid.com    / admin123");
        System.out.println("Operator: operator@powergrid.com / operator123");
        System.out.println("User:     user@powergrid.com     / user123");
        System.out.println("Viewer:   viewer@powergrid.com   / viewer123");
        System.out.println("===============================\n");
    }
} 