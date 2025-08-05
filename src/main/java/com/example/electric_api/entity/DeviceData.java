package com.example.electric_api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_data", indexes = {
    @Index(name = "idx_device_timestamp", columnList = "device_id, timestamp"),
    @Index(name = "idx_timestamp", columnList = "timestamp")
})
public class DeviceData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(precision = 10, scale = 2)
    private BigDecimal voltage;

    @Column(precision = 10, scale = 2)
    private BigDecimal current;

    @Column(precision = 10, scale = 2)
    private BigDecimal power;

    @Column(precision = 8, scale = 2)
    private BigDecimal frequency;

    @Column(precision = 8, scale = 2)
    private BigDecimal temperature;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public DeviceData() {}

    public DeviceData(Device device, BigDecimal voltage, BigDecimal current, BigDecimal power, 
                     BigDecimal frequency, BigDecimal temperature, LocalDateTime timestamp) {
        this.device = device;
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.frequency = frequency;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }

    public BigDecimal getVoltage() { return voltage; }
    public void setVoltage(BigDecimal voltage) { this.voltage = voltage; }

    public BigDecimal getCurrent() { return current; }
    public void setCurrent(BigDecimal current) { this.current = current; }

    public BigDecimal getPower() { return power; }
    public void setPower(BigDecimal power) { this.power = power; }

    public BigDecimal getFrequency() { return frequency; }
    public void setFrequency(BigDecimal frequency) { this.frequency = frequency; }

    public BigDecimal getTemperature() { return temperature; }
    public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 