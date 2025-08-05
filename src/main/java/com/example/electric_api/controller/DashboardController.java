package com.example.electric_api.controller;

import com.example.electric_api.service.DashboardService;
import com.example.electric_api.service.FakeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:8000"})
@Tag(name = "Dashboard", description = "APIs for dashboard overview and statistics")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private FakeDataService fakeDataService;

    @GetMapping("/overview")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get dashboard overview with statistics and charts data")
    public ResponseEntity<?> getOverview() {
        try {
            // Always use fake data for demo purposes
            System.out.println("🎯 Generating comprehensive dashboard overview data");
            
            Map<String, Object> overview = fakeDataService.getDashboardOverview();
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", overview);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Dashboard overview loaded successfully");
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ Error loading dashboard overview: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load dashboard overview");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get detailed device statistics")
    public ResponseEntity<?> getStatistics() {
        try {
            Map<String, Object> statistics = fakeDataService.getFakeStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("statistics", statistics);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Statistics loaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ Error loading statistics: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load statistics");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/alerts/summary")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') or hasRole('USER') or hasRole('VIEWER')")
    @Operation(summary = "Get alerts summary for dashboard")
    public ResponseEntity<?> getAlertsSummary() {
        try {
            Map<String, Object> alertsSummary = fakeDataService.getFakeAlertsSummary();
            
            Map<String, Object> response = new HashMap<>();
            response.put("alerts", alertsSummary);
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Alerts summary loaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ Error loading alerts summary: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load alerts summary");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
} 