package com.example.electric_api.dto;

import java.math.BigDecimal;

public class PowerFacility {
    private String id;
    private String name;
    private String type; // power_plant, substation, transmission_tower, distribution_center, transformer, renewable
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer voltage; // kV
    private Integer capacity; // MW
    private String status; // online, offline, maintenance, warning
    private String operator;
    private String commissioned;
    private String description;

    // Constructors
    public PowerFacility() {}

    public PowerFacility(String id, String name, String type, BigDecimal latitude, BigDecimal longitude,
                        Integer voltage, Integer capacity, String status, String operator, 
                        String commissioned, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.voltage = voltage;
        this.capacity = capacity;
        this.status = status;
        this.operator = operator;
        this.commissioned = commissioned;
        this.description = description;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public Integer getVoltage() { return voltage; }
    public void setVoltage(Integer voltage) { this.voltage = voltage; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getCommissioned() { return commissioned; }
    public void setCommissioned(String commissioned) { this.commissioned = commissioned; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
} 