package com.example.electric_api.controller;

import com.example.electric_api.entity.Alert;
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
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Tag(name = "Alert Management", description = "APIs for managing system alerts and notifications")
@SecurityRequirement(name = "Bearer Authentication")
public class AlertController {

    @Autowired
    private FakeDataService fakeDataService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get all alerts with pagination and filtering")
    public ResponseEntity<?> getAllAlerts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) Boolean resolved) {
        
        try {
            // Use fake data for now with filtering support
            List<Alert> fakeAlerts = fakeDataService.filterFakeAlerts(level, status, type, deviceId, resolved);
            
            // Simple pagination for fake data
            int start = page * size;
            int end = Math.min(start + size, fakeAlerts.size());
            List<Alert> pagedAlerts = fakeAlerts.subList(start, end);
            
            Map<String, Object> response = new HashMap<>();
            response.put("alerts", pagedAlerts);
            response.put("currentPage", page);
            response.put("totalItems", fakeAlerts.size());
            response.put("totalPages", (int) Math.ceil((double) fakeAlerts.size() / size));
            response.put("hasNext", end < fakeAlerts.size());
            response.put("hasPrevious", page > 0);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake alert data");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching alerts: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get a single alert by ID")
    public ResponseEntity<?> getAlertById(@PathVariable Long id) {
        try {
            List<Alert> fakeAlerts = fakeDataService.getFakeAlerts();
            Alert alert = fakeAlerts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
                
            if (alert != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("alert", alert);
                response.put("dataSource", "FAKE_DATA");
                response.put("message", "Using fake alert data");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Alert not found: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER')")
    @Operation(summary = "Create a new alert")
    public ResponseEntity<?> createAlert(@Valid @RequestBody AlertCreateRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Alert creation simulated - using fake data mode");
            response.put("alert", createFakeAlertFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error creating alert: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER')")
    @Operation(summary = "Update an existing alert by ID")
    public ResponseEntity<?> updateAlert(@PathVariable Long id, @Valid @RequestBody AlertUpdateRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Alert update simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error updating alert: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER')")
    @Operation(summary = "Mark an alert as resolved by ID")
    public ResponseEntity<?> resolveAlert(@PathVariable Long id) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Alert resolution simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error resolving alert: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an alert by ID")
    public ResponseEntity<?> deleteAlert(@PathVariable Long id) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Alert deletion simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error deleting alert: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get alert statistics")
    public ResponseEntity<?> getAlertStatistics(
            @RequestParam(required = false) String period) {
        try {
            List<Alert> fakeAlerts = fakeDataService.getFakeAlerts();
            
            Map<String, Object> stats = new HashMap<>();
            
            // Calculate statistics from fake data
            long totalAlerts = fakeAlerts.size();
            long resolvedAlerts = fakeAlerts.stream()
                .filter(alert -> alert.getResolvedAt() != null)
                .count();
            long unresolvedAlerts = totalAlerts - resolvedAlerts;
            long criticalAlerts = fakeAlerts.stream()
                .filter(alert -> "CRITICAL".equals(alert.getAlertLevel().getName()) && alert.getResolvedAt() == null)
                .count();
            
            // Alert level distribution
            Map<String, Long> levelDistribution = new HashMap<>();
            levelDistribution.put("info", fakeAlerts.stream().filter(a -> "INFO".equals(a.getAlertLevel().getName())).count());
            levelDistribution.put("warning", fakeAlerts.stream().filter(a -> "WARNING".equals(a.getAlertLevel().getName())).count());
            levelDistribution.put("error", fakeAlerts.stream().filter(a -> "ERROR".equals(a.getAlertLevel().getName())).count());
            levelDistribution.put("critical", fakeAlerts.stream().filter(a -> "CRITICAL".equals(a.getAlertLevel().getName())).count());
            
            // Alert type distribution
            Map<String, Long> typeDistribution = new HashMap<>();
            typeDistribution.put("over_voltage", fakeAlerts.stream().filter(a -> a.getAlertType() == Alert.AlertType.OVER_VOLTAGE).count());
            typeDistribution.put("over_current", fakeAlerts.stream().filter(a -> a.getAlertType() == Alert.AlertType.OVER_CURRENT).count());
            typeDistribution.put("over_temperature", fakeAlerts.stream().filter(a -> a.getAlertType() == Alert.AlertType.OVER_TEMPERATURE).count());
            typeDistribution.put("device_offline", fakeAlerts.stream().filter(a -> a.getAlertType() == Alert.AlertType.DEVICE_OFFLINE).count());
            
            stats.put("totalAlerts", totalAlerts);
            stats.put("resolvedAlerts", resolvedAlerts);
            stats.put("unresolvedAlerts", unresolvedAlerts);
            stats.put("criticalAlerts", criticalAlerts);
            stats.put("levelDistribution", levelDistribution);
            stats.put("typeDistribution", typeDistribution);
            stats.put("period", period != null ? period : "all");
            stats.put("lastUpdated", java.time.LocalDateTime.now());
            stats.put("dataSource", "FAKE_DATA");
            stats.put("message", "Using fake alert statistics");
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching alert statistics: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/unresolved")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get unresolved alerts with pagination")
    public ResponseEntity<?> getUnresolvedAlerts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            List<Alert> fakeAlerts = fakeDataService.getFakeAlerts();
            List<Alert> unresolvedAlerts = fakeAlerts.stream()
                .filter(alert -> alert.getResolvedAt() == null)
                .collect(java.util.stream.Collectors.toList());
            
            // Simple pagination
            int start = page * size;
            int end = Math.min(start + size, unresolvedAlerts.size());
            List<Alert> pagedAlerts = unresolvedAlerts.subList(start, end);
            
            Map<String, Object> response = new HashMap<>();
            response.put("alerts", pagedAlerts);
            response.put("count", unresolvedAlerts.size());
            response.put("currentPage", page);
            response.put("totalPages", (int) Math.ceil((double) unresolvedAlerts.size() / size));
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake unresolved alerts");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching unresolved alerts: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/critical")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get critical alerts")
    public ResponseEntity<?> getCriticalAlerts() {
        try {
            List<Alert> fakeAlerts = fakeDataService.getFakeAlerts();
            List<Alert> criticalAlerts = fakeAlerts.stream()
                .filter(alert -> "CRITICAL".equals(alert.getAlertLevel().getName()) && alert.getResolvedAt() == null)
                .collect(java.util.stream.Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("criticalAlerts", criticalAlerts);
            response.put("count", criticalAlerts.size());
            response.put("lastUpdated", java.time.LocalDateTime.now());
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake critical alerts");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching critical alerts: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Helper method
    private Alert createFakeAlertFromRequest(AlertCreateRequest request) {
        Alert fakeAlert = new Alert();
        fakeAlert.setId(System.currentTimeMillis());
        fakeAlert.setTitle(request.getTitle());
        fakeAlert.setDescription(request.getDescription());
        if (request.getThresholdValue() != null) {
            fakeAlert.setThresholdValue(new BigDecimal(request.getThresholdValue()));
        }
        if (request.getActualValue() != null) {
            fakeAlert.setActualValue(new BigDecimal(request.getActualValue()));
        }
        fakeAlert.setCreatedAt(java.time.LocalDateTime.now());
        
        // Set fake relationships
        if (request.getAlertType() != null) {
            fakeAlert.setAlertType(Alert.AlertType.valueOf(request.getAlertType().toUpperCase()));
        }
        
        return fakeAlert;
    }

    // DTOs
    public static class AlertCreateRequest {
        private Long deviceId;
        private Long alertLevelId;
        private Long alertStatusId;
        private String title;
        private String description;
        private String alertType;
        private String thresholdValue;
        private String actualValue;

        // Getters and setters
        public Long getDeviceId() { return deviceId; }
        public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
        
        public Long getAlertLevelId() { return alertLevelId; }
        public void setAlertLevelId(Long alertLevelId) { this.alertLevelId = alertLevelId; }
        
        public Long getAlertStatusId() { return alertStatusId; }
        public void setAlertStatusId(Long alertStatusId) { this.alertStatusId = alertStatusId; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getAlertType() { return alertType; }
        public void setAlertType(String alertType) { this.alertType = alertType; }
        
        public String getThresholdValue() { return thresholdValue; }
        public void setThresholdValue(String thresholdValue) { this.thresholdValue = thresholdValue; }
        
        public String getActualValue() { return actualValue; }
        public void setActualValue(String actualValue) { this.actualValue = actualValue; }
    }

    public static class AlertUpdateRequest extends AlertCreateRequest {
        private String resolvedAt;
        private Long resolvedByUserId;
        
        public String getResolvedAt() { return resolvedAt; }
        public void setResolvedAt(String resolvedAt) { this.resolvedAt = resolvedAt; }
        
        public Long getResolvedByUserId() { return resolvedByUserId; }
        public void setResolvedByUserId(Long resolvedByUserId) { this.resolvedByUserId = resolvedByUserId; }
    }
} 