package com.example.electric_api.dto;

import java.math.BigDecimal;
import java.util.List;

public class PowerLine {
    private String id;
    private String name;
    private String from;
    private String to;
    private List<BigDecimal[]> positions; // [[lat, lng], [lat, lng], ...]
    private Integer voltage; // kV
    private Integer capacity; // MW
    private String status; // active, inactive, maintenance
    private String type; // transmission, distribution

    // Constructors
    public PowerLine() {}

    public PowerLine(String id, String name, String from, String to, List<BigDecimal[]> positions,
                    Integer voltage, Integer capacity, String status, String type) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.positions = positions;
        this.voltage = voltage;
        this.capacity = capacity;
        this.status = status;
        this.type = type;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public List<BigDecimal[]> getPositions() { return positions; }
    public void setPositions(List<BigDecimal[]> positions) { this.positions = positions; }

    public Integer getVoltage() { return voltage; }
    public void setVoltage(Integer voltage) { this.voltage = voltage; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
} 