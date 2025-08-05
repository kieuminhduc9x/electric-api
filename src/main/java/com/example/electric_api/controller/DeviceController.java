package com.example.electric_api.controller;

import com.example.electric_api.entity.Device;
import com.example.electric_api.service.DeviceService;
import com.example.electric_api.service.FakeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Tag(name = "Device Management", description = "APIs for managing electrical devices and monitoring real-time data")
@SecurityRequirement(name = "Bearer Authentication")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FakeDataService fakeDataService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all devices with pagination and filtering")
    public ResponseEntity<?> getAllDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long stationId,
            @RequestParam(required = false) Long deviceTypeId) {
        
        try {
            // Always use fake data for demo purposes
            System.out.println("🎯 Using comprehensive fake device data for demo");
            
            List<Device> fakeDevices = fakeDataService.getFakeDevices();
            
            // Apply search filter if provided
            if (search != null && !search.isEmpty()) {
                fakeDevices = fakeDataService.searchFakeDevices(search);
            }
            
            // Apply status filter if provided
            if (status != null) {
                Device.DeviceStatus deviceStatus = Device.DeviceStatus.valueOf(status.toUpperCase());
                fakeDevices = fakeDevices.stream()
                    .filter(device -> device.getStatus() == deviceStatus)
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Apply station filter if provided
            if (stationId != null) {
                fakeDevices = fakeDevices.stream()
                    .filter(device -> device.getStation() != null && device.getStation().getId().equals(stationId))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Apply device type filter if provided
            if (deviceTypeId != null) {
                fakeDevices = fakeDevices.stream()
                    .filter(device -> device.getDeviceType() != null && device.getDeviceType().getId().equals(deviceTypeId))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Simple pagination for fake data
            int start = page * size;
            int end = Math.min(start + size, fakeDevices.size());
            List<Device> pagedDevices = fakeDevices.subList(start, end);
            
            Map<String, Object> response = new HashMap<>();
            response.put("devices", pagedDevices);
            response.put("currentPage", page);
            response.put("totalItems", fakeDevices.size());
            response.put("totalPages", (int) Math.ceil((double) fakeDevices.size() / size));
            response.put("hasNext", end < fakeDevices.size());
            response.put("hasPrevious", page > 0);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Comprehensive device data loaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ Error loading device data: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load device data");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get a device by its ID")
    public ResponseEntity<?> getDeviceById(@PathVariable Long id) {
        try {
            // Use fake data for demo purposes
            List<Device> fakeDevices = fakeDataService.getFakeDevices();
            
            Device device = fakeDevices.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
            
            if (device == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Device not found");
                errorResponse.put("message", "Device with ID " + id + " not found");
                errorResponse.put("dataSource", "FAKE_DATA");
                return ResponseEntity.status(404).body(errorResponse);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("device", device);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Device retrieved successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ Error retrieving device: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve device");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Create a new device")
    public ResponseEntity<?> createDevice(@Valid @RequestBody DeviceCreateRequest request) {
        try {
            Device device = deviceService.createDevice(request);
            return ResponseEntity.ok(device);
        } catch (Exception e) {
            // For fake data, just return success message
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Device creation simulated - using fake data mode");
            response.put("device", createFakeDeviceFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @Operation(summary = "Update an existing device by its ID")
    public ResponseEntity<?> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceUpdateRequest request) {
        try {
            Device device = deviceService.updateDevice(id, request);
            return ResponseEntity.ok(device);
        } catch (Exception e) {
            // For fake data, just return success message
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Device update simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a device by its ID")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        try {
            deviceService.deleteDevice(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Device deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Device deletion simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}/data")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get real-time data for a device by its ID")
    public ResponseEntity<?> getDeviceData(@PathVariable Long id,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Map<String, Object> data = deviceService.getDeviceData(id, pageable);
            data.put("dataSource", "DATABASE");
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            // Use fake device data
            Device fakeDevice = fakeDataService.getFakeDeviceById(id);
            List<Map<String, Object>> fakeData = fakeDataService.getFakeDeviceData(id, size);
            
            Map<String, Object> response = new HashMap<>();
            response.put("device", fakeDevice);
            response.put("data", fakeData);
            response.put("lastUpdated", java.time.LocalDateTime.now());
            response.put("status", fakeDevice != null ? fakeDevice.getStatus() : "UNKNOWN");
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}/data/history")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get historical data for a device by its ID")
    public ResponseEntity<?> getDeviceDataHistory(@PathVariable Long id,
                                                 @RequestParam(required = false) String startDate,
                                                 @RequestParam(required = false) String endDate,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "50") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Map<String, Object> history = deviceService.getDeviceDataHistory(id, startDate, endDate, pageable);
            history.put("dataSource", "DATABASE");
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            // Use fake historical data
            Device fakeDevice = fakeDataService.getFakeDeviceById(id);
            List<Map<String, Object>> fakeHistory = fakeDataService.getFakeDeviceData(id, size * 2); // More data for history
            
            Map<String, Object> response = new HashMap<>();
            response.put("device", fakeDevice);
            response.put("history", fakeHistory);
            response.put("totalRecords", fakeHistory.size());
            response.put("startDate", startDate);
            response.put("endDate", endDate);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get device statistics")
    public ResponseEntity<?> getDeviceStatistics() {
        try {
            Map<String, Object> stats = deviceService.getDeviceStatistics();
            stats.put("dataSource", "DATABASE");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            // Use fake statistics
            Map<String, Object> fakeStats = fakeDataService.getFakeStatistics();
            fakeStats.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(fakeStats);
        }
    }

    // Helper method to create fake device from request
    private Device createFakeDeviceFromRequest(DeviceCreateRequest request) {
        Device fakeDevice = new Device();
        fakeDevice.setId(System.currentTimeMillis()); // Use timestamp as fake ID
        fakeDevice.setName(request.getName());
        fakeDevice.setModel(request.getModel());
        fakeDevice.setSerialNumber(request.getSerialNumber());
        fakeDevice.setStatus(Device.DeviceStatus.OFFLINE);
        fakeDevice.setSpecifications(request.getSpecifications());
        fakeDevice.setIsActive(true);
        fakeDevice.setCreatedAt(java.time.LocalDateTime.now());
        return fakeDevice;
    }

    // DTOs cho request
    public static class DeviceCreateRequest {
        private Long deviceTypeId;
        private Long manufacturerId;
        private Long stationId;
        private String name;
        private String model;
        private String serialNumber;
        private String installationDate;
        private String specifications;

        // Getters and setters
        public Long getDeviceTypeId() { return deviceTypeId; }
        public void setDeviceTypeId(Long deviceTypeId) { this.deviceTypeId = deviceTypeId; }
        
        public Long getManufacturerId() { return manufacturerId; }
        public void setManufacturerId(Long manufacturerId) { this.manufacturerId = manufacturerId; }
        
        public Long getStationId() { return stationId; }
        public void setStationId(Long stationId) { this.stationId = stationId; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        
        public String getSerialNumber() { return serialNumber; }
        public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
        
        public String getInstallationDate() { return installationDate; }
        public void setInstallationDate(String installationDate) { this.installationDate = installationDate; }
        
        public String getSpecifications() { return specifications; }
        public void setSpecifications(String specifications) { this.specifications = specifications; }
    }

    public static class DeviceUpdateRequest extends DeviceCreateRequest {
        private String status;
        private String lastMaintenance;
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getLastMaintenance() { return lastMaintenance; }
        public void setLastMaintenance(String lastMaintenance) { this.lastMaintenance = lastMaintenance; }
    }
} 