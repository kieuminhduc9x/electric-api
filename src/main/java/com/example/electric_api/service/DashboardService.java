package com.example.electric_api.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class DashboardService {
    
    // This service will be used when we have real database connection
    // For now, it's a placeholder for future implementation
    
    public Map<String, Object> getOverview() {
        // This will be implemented when we have real database
        throw new RuntimeException("Database not available - using fake data");
    }
    
    public Map<String, Object> getStatistics() {
        // This will be implemented when we have real database
        throw new RuntimeException("Database not available - using fake data");
    }
    
    public Map<String, Object> getAlertsSummary() {
        // This will be implemented when we have real database
        throw new RuntimeException("Database not available - using fake data");
    }
} 