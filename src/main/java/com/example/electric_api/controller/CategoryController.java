package com.example.electric_api.controller;

import com.example.electric_api.entity.*;
import com.example.electric_api.repository.*;
import com.example.electric_api.service.FakeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Tag(name = "Category Management", description = "APIs for managing system categories (Device Types, Manufacturers, Stations, Alert Levels & Statuses)")
@SecurityRequirement(name = "Bearer Authentication")
public class CategoryController {

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private AlertLevelRepository alertLevelRepository;

    @Autowired
    private AlertStatusRepository alertStatusRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FakeDataService fakeDataService;

    // ================== DEVICE TYPES ==================
    @GetMapping("/device-types")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all active device types")
    public ResponseEntity<?> getAllDeviceTypes() {
        try {
            List<DeviceType> deviceTypes = deviceTypeRepository.findByIsActiveTrue();
            Map<String, Object> response = new HashMap<>();
            response.put("deviceTypes", deviceTypes);
            response.put("dataSource", "DATABASE");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Fallback to fake data
            System.out.println("⚠️ Database unavailable, using fake device types: " + e.getMessage());
            List<DeviceType> fakeDeviceTypes = fakeDataService.getFakeDeviceTypes();
            Map<String, Object> response = new HashMap<>();
            response.put("deviceTypes", fakeDeviceTypes);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/device-types")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Create a new device type")
    public ResponseEntity<?> createDeviceType(@Valid @RequestBody DeviceTypeRequest request) {
        try {
            if (deviceTypeRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Device type with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            DeviceType deviceType = new DeviceType();
            deviceType.setName(request.getName());
            deviceType.setDescription(request.getDescription());
            deviceType.setIcon(request.getIcon());
            deviceType.setSpecifications(request.getSpecifications());
            deviceType.setIsActive(true);

            DeviceType saved = deviceTypeRepository.save(deviceType);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Device type creation simulated - using fake data mode");
            response.put("deviceType", createFakeDeviceTypeFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/device-types/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Update an existing device type by ID")
    public ResponseEntity<?> updateDeviceType(@PathVariable Long id, @Valid @RequestBody DeviceTypeRequest request) {
        try {
            DeviceType deviceType = deviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device type not found"));

            if (!deviceType.getName().equals(request.getName()) && 
                deviceTypeRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Device type with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            deviceType.setName(request.getName());
            deviceType.setDescription(request.getDescription());
            deviceType.setIcon(request.getIcon());
            deviceType.setSpecifications(request.getSpecifications());

            DeviceType saved = deviceTypeRepository.save(deviceType);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Device type update simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/device-types/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a device type by ID")
    public ResponseEntity<?> deleteDeviceType(@PathVariable Long id) {
        try {
            DeviceType deviceType = deviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device type not found"));
            
            deviceType.setIsActive(false);
            deviceTypeRepository.save(deviceType);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Device type deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Device type deletion simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    // ================== MANUFACTURERS ==================
    @GetMapping("/manufacturers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all active manufacturers")
    public ResponseEntity<?> getAllManufacturers() {
        try {
            List<Manufacturer> manufacturers = manufacturerRepository.findByIsActiveTrue();
            Map<String, Object> response = new HashMap<>();
            response.put("manufacturers", manufacturers);
            response.put("dataSource", "DATABASE");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("⚠️ Database unavailable, using fake manufacturers: " + e.getMessage());
            List<Manufacturer> fakeManufacturers = fakeDataService.getFakeManufacturers();
            Map<String, Object> response = new HashMap<>();
            response.put("manufacturers", fakeManufacturers);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/manufacturers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Create a new manufacturer")
    public ResponseEntity<?> createManufacturer(@Valid @RequestBody ManufacturerRequest request) {
        try {
            if (manufacturerRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Manufacturer with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(request.getName());
            manufacturer.setDescription(request.getDescription());
            manufacturer.setCountry(request.getCountry());
            manufacturer.setWebsite(request.getWebsite());
            manufacturer.setContactInfo(request.getContactInfo());
            manufacturer.setIsActive(true);

            Manufacturer saved = manufacturerRepository.save(manufacturer);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Manufacturer creation simulated - using fake data mode");
            response.put("manufacturer", createFakeManufacturerFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/manufacturers/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Update an existing manufacturer by ID")
    public ResponseEntity<?> updateManufacturer(@PathVariable Long id, @Valid @RequestBody ManufacturerRequest request) {
        try {
            Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

            if (!manufacturer.getName().equals(request.getName()) && 
                manufacturerRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Manufacturer with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            manufacturer.setName(request.getName());
            manufacturer.setDescription(request.getDescription());
            manufacturer.setCountry(request.getCountry());
            manufacturer.setWebsite(request.getWebsite());
            manufacturer.setContactInfo(request.getContactInfo());

            Manufacturer saved = manufacturerRepository.save(manufacturer);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Manufacturer update simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/manufacturers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a manufacturer by ID")
    public ResponseEntity<?> deleteManufacturer(@PathVariable Long id) {
        try {
            Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
            
            manufacturer.setIsActive(false);
            manufacturerRepository.save(manufacturer);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Manufacturer deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Manufacturer deletion simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    // ================== STATIONS ==================
    @GetMapping("/stations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all active stations")
    public ResponseEntity<?> getAllStations() {
        try {
            List<Station> stations = stationRepository.findByIsActiveTrue();
            Map<String, Object> response = new HashMap<>();
            response.put("stations", stations);
            response.put("dataSource", "DATABASE");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("⚠️ Database unavailable, using fake stations: " + e.getMessage());
            List<Station> fakeStations = fakeDataService.getFakeStations();
            Map<String, Object> response = new HashMap<>();
            response.put("stations", fakeStations);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/stations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Create a new station")
    public ResponseEntity<?> createStation(@Valid @RequestBody StationRequest request) {
        try {
            if (stationRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Station with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            Station station = new Station();
            station.setName(request.getName());
            station.setLocation(request.getLocation());
            station.setLatitude(request.getLatitude());
            station.setLongitude(request.getLongitude());
            station.setAddress(request.getAddress());
            station.setIsActive(true);

            Station saved = stationRepository.save(station);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Station creation simulated - using fake data mode");
            response.put("station", createFakeStationFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    // ================== ALERT LEVELS ==================
    @GetMapping("/alert-levels")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all active alert levels")
    public ResponseEntity<?> getAllAlertLevels() {
        try {
            List<AlertLevel> alertLevels = alertLevelRepository.findByIsActiveTrue();
            Map<String, Object> response = new HashMap<>();
            response.put("alertLevels", alertLevels);
            response.put("dataSource", "DATABASE");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("⚠️ Database unavailable, using fake alert levels: " + e.getMessage());
            List<AlertLevel> fakeAlertLevels = fakeDataService.getFakeAlertLevels();
            Map<String, Object> response = new HashMap<>();
            response.put("alertLevels", fakeAlertLevels);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/alert-levels")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new alert level")
    public ResponseEntity<?> createAlertLevel(@Valid @RequestBody AlertLevelRequest request) {
        try {
            if (alertLevelRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Alert level with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            AlertLevel alertLevel = new AlertLevel();
            alertLevel.setName(request.getName());
            alertLevel.setDescription(request.getDescription());
            alertLevel.setSeverity(AlertLevel.Severity.valueOf(request.getSeverity().toUpperCase()));
            alertLevel.setThreshold(request.getThreshold());
            alertLevel.setColor(request.getColor());
            alertLevel.setIsActive(true);

            AlertLevel saved = alertLevelRepository.save(alertLevel);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Alert level creation simulated - using fake data mode");
            response.put("alertLevel", createFakeAlertLevelFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    // ================== ALERT STATUSES ==================
    @GetMapping("/alert-statuses")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all active alert statuses")
    public ResponseEntity<?> getAllAlertStatuses() {
        try {
            List<AlertStatus> alertStatuses = alertStatusRepository.findByIsActiveTrue();
            Map<String, Object> response = new HashMap<>();
            response.put("alertStatuses", alertStatuses);
            response.put("dataSource", "DATABASE");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("⚠️ Database unavailable, using fake alert statuses: " + e.getMessage());
            List<AlertStatus> fakeAlertStatuses = fakeDataService.getFakeAlertStatuses();
            Map<String, Object> response = new HashMap<>();
            response.put("alertStatuses", fakeAlertStatuses);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/alert-statuses")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new alert status")
    public ResponseEntity<?> createAlertStatus(@Valid @RequestBody AlertStatusRequest request) {
        try {
            if (alertStatusRepository.existsByName(request.getName())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Alert status with this name already exists");
                return ResponseEntity.badRequest().body(error);
            }

            AlertStatus alertStatus = new AlertStatus();
            alertStatus.setName(request.getName());
            alertStatus.setDescription(request.getDescription());
            alertStatus.setColor(request.getColor());
            alertStatus.setPriority(request.getPriority());
            alertStatus.setIsActive(true);

            AlertStatus saved = alertStatusRepository.save(alertStatus);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Alert status creation simulated - using fake data mode");
            response.put("alertStatus", createFakeAlertStatusFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    // ================== HELPER METHODS ==================
    private DeviceType createFakeDeviceTypeFromRequest(DeviceTypeRequest request) {
        DeviceType fakeDeviceType = new DeviceType();
        fakeDeviceType.setId(System.currentTimeMillis());
        fakeDeviceType.setName(request.getName());
        fakeDeviceType.setDescription(request.getDescription());
        fakeDeviceType.setIcon(request.getIcon());
        fakeDeviceType.setSpecifications(request.getSpecifications());
        fakeDeviceType.setIsActive(true);
        fakeDeviceType.setCreatedAt(java.time.LocalDateTime.now());
        return fakeDeviceType;
    }

    private Manufacturer createFakeManufacturerFromRequest(ManufacturerRequest request) {
        Manufacturer fakeManufacturer = new Manufacturer();
        fakeManufacturer.setId(System.currentTimeMillis());
        fakeManufacturer.setName(request.getName());
        fakeManufacturer.setDescription(request.getDescription());
        fakeManufacturer.setCountry(request.getCountry());
        fakeManufacturer.setWebsite(request.getWebsite());
        fakeManufacturer.setContactInfo(request.getContactInfo());
        fakeManufacturer.setIsActive(true);
        fakeManufacturer.setCreatedAt(java.time.LocalDateTime.now());
        return fakeManufacturer;
    }

    private Station createFakeStationFromRequest(StationRequest request) {
        Station fakeStation = new Station();
        fakeStation.setId(System.currentTimeMillis());
        fakeStation.setName(request.getName());
        fakeStation.setLocation(request.getLocation());
        fakeStation.setLatitude(request.getLatitude());
        fakeStation.setLongitude(request.getLongitude());
        fakeStation.setAddress(request.getAddress());
        fakeStation.setIsActive(true);
        fakeStation.setCreatedAt(java.time.LocalDateTime.now());
        return fakeStation;
    }

    private AlertLevel createFakeAlertLevelFromRequest(AlertLevelRequest request) {
        AlertLevel fakeAlertLevel = new AlertLevel();
        fakeAlertLevel.setId(System.currentTimeMillis());
        fakeAlertLevel.setName(request.getName());
        fakeAlertLevel.setDescription(request.getDescription());
        fakeAlertLevel.setSeverity(AlertLevel.Severity.valueOf(request.getSeverity().toUpperCase()));
        fakeAlertLevel.setThreshold(request.getThreshold());
        fakeAlertLevel.setColor(request.getColor());
        fakeAlertLevel.setIsActive(true);
        fakeAlertLevel.setCreatedAt(java.time.LocalDateTime.now());
        return fakeAlertLevel;
    }

    private AlertStatus createFakeAlertStatusFromRequest(AlertStatusRequest request) {
        AlertStatus fakeAlertStatus = new AlertStatus();
        fakeAlertStatus.setId(System.currentTimeMillis());
        fakeAlertStatus.setName(request.getName());
        fakeAlertStatus.setDescription(request.getDescription());
        fakeAlertStatus.setColor(request.getColor());
        fakeAlertStatus.setPriority(request.getPriority());
        fakeAlertStatus.setIsActive(true);
        fakeAlertStatus.setCreatedAt(java.time.LocalDateTime.now());
        return fakeAlertStatus;
    }

    // ================== DTOs ==================
    public static class DeviceTypeRequest {
        private String name;
        private String description;
        private String icon;
        private String specifications;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        public String getSpecifications() { return specifications; }
        public void setSpecifications(String specifications) { this.specifications = specifications; }
    }

    public static class ManufacturerRequest {
        private String name;
        private String description;
        private String country;
        private String website;
        private String contactInfo;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getWebsite() { return website; }
        public void setWebsite(String website) { this.website = website; }
        public String getContactInfo() { return contactInfo; }
        public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    }

    public static class StationRequest {
        private String name;
        private String location;
        private java.math.BigDecimal latitude;
        private java.math.BigDecimal longitude;
        private String address;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public java.math.BigDecimal getLatitude() { return latitude; }
        public void setLatitude(java.math.BigDecimal latitude) { this.latitude = latitude; }
        public java.math.BigDecimal getLongitude() { return longitude; }
        public void setLongitude(java.math.BigDecimal longitude) { this.longitude = longitude; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public static class AlertLevelRequest {
        private String name;
        private String description;
        private String severity;
        private java.math.BigDecimal threshold;
        private String color;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        public java.math.BigDecimal getThreshold() { return threshold; }
        public void setThreshold(java.math.BigDecimal threshold) { this.threshold = threshold; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }

    public static class AlertStatusRequest {
        private String name;
        private String description;
        private String color;
        private Integer priority;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public Integer getPriority() { return priority; }
        public void setPriority(Integer priority) { this.priority = priority; }
    }
} 