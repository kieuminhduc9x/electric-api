package com.example.electric_api.controller;

import com.example.electric_api.dto.PowerFacility;
import com.example.electric_api.dto.PowerLine;
import com.example.electric_api.service.FakeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grid-map")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:8000"})
@Tag(name = "Grid Map", description = "APIs for power grid map visualization")
public class GridMapController {

    @Autowired
    private FakeDataService fakeDataService;

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get all power grid data for map visualization")
    public ResponseEntity<?> getGridMapData() {
        try {
            System.out.println("🗺️ Loading power grid map data...");
            
            List<PowerFacility> facilities = fakeDataService.getFakePowerFacilities();
            List<PowerLine> lines = fakeDataService.getFakePowerLines();
            
            Map<String, Object> response = new HashMap<>();
            response.put("facilities", facilities);
            response.put("powerLines", lines);
            response.put("totalFacilities", facilities.size());
            response.put("totalLines", lines.size());
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Power grid map data loaded successfully");
            
            System.out.println("✅ Grid map data loaded: " + facilities.size() + " facilities, " + lines.size() + " lines");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ Error loading grid map data: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load grid map data");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/facilities")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get all power facilities for map markers")
    public ResponseEntity<?> getPowerFacilities() {
        try {
            List<PowerFacility> facilities = fakeDataService.getFakePowerFacilities();
            
            Map<String, Object> response = new HashMap<>();
            response.put("facilities", facilities);
            response.put("total", facilities.size());
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Power facilities loaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load power facilities");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/lines")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get all power transmission and distribution lines")
    public ResponseEntity<?> getPowerLines() {
        try {
            List<PowerLine> lines = fakeDataService.getFakePowerLines();
            
            Map<String, Object> response = new HashMap<>();
            response.put("powerLines", lines);
            response.put("total", lines.size());
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Power lines loaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load power lines");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/statistics")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get grid map statistics")
    public ResponseEntity<?> getGridMapStatistics() {
        try {
            List<PowerFacility> facilities = fakeDataService.getFakePowerFacilities();
            List<PowerLine> lines = fakeDataService.getFakePowerLines();
            
            // Calculate statistics
            long onlineFacilities = facilities.stream()
                .filter(f -> "online".equals(f.getStatus()))
                .count();
            
            long maintenanceFacilities = facilities.stream()
                .filter(f -> "maintenance".equals(f.getStatus()))
                .count();
            
            long offlineFacilities = facilities.stream()
                .filter(f -> "offline".equals(f.getStatus()))
                .count();
            
            long activeLines = lines.stream()
                .filter(l -> "active".equals(l.getStatus()))
                .count();
            
            int totalCapacity = facilities.stream()
                .mapToInt(PowerFacility::getCapacity)
                .sum();
            
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalFacilities", facilities.size());
            statistics.put("onlineFacilities", onlineFacilities);
            statistics.put("maintenanceFacilities", maintenanceFacilities);
            statistics.put("offlineFacilities", offlineFacilities);
            statistics.put("totalLines", lines.size());
            statistics.put("activeLines", activeLines);
            statistics.put("totalCapacity", totalCapacity);
            statistics.put("dataSource", "FAKE_DATA");
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to load grid statistics");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("dataSource", "ERROR");
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 