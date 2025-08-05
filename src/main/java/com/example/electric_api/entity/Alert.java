package com.example.electric_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts", indexes = {
    @Index(name = "idx_device_created", columnList = "device_id, created_at"),
    @Index(name = "idx_alert_level", columnList = "alert_level_id"),
    @Index(name = "idx_alert_status", columnList = "alert_status_id")
})
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_level_id", nullable = false)
    private AlertLevel alertLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_status_id", nullable = false)
    private AlertStatus alertStatus;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType alertType;

    @Column(precision = 10, scale = 2)
    private BigDecimal thresholdValue;

    @Column(precision = 10, scale = 2)
    private BigDecimal actualValue;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by_user_id")
    private User resolvedByUser;

    // Enum for alert types
    public enum AlertType {
        OVER_VOLTAGE, OVER_CURRENT, OVER_TEMPERATURE, UNDER_VOLTAGE, 
        UNDER_CURRENT, DEVICE_OFFLINE, DEVICE_ERROR, MAINTENANCE_DUE
    }

    // Constructors
    public Alert() {}

    public Alert(Device device, AlertLevel alertLevel, AlertStatus alertStatus, 
                String title, AlertType alertType) {
        this.device = device;
        this.alertLevel = alertLevel;
        this.alertStatus = alertStatus;
        this.title = title;
        this.alertType = alertType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }

    public AlertLevel getAlertLevel() { return alertLevel; }
    public void setAlertLevel(AlertLevel alertLevel) { this.alertLevel = alertLevel; }

    public AlertStatus getAlertStatus() { return alertStatus; }
    public void setAlertStatus(AlertStatus alertStatus) { this.alertStatus = alertStatus; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public AlertType getAlertType() { return alertType; }
    public void setAlertType(AlertType alertType) { this.alertType = alertType; }

    public BigDecimal getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(BigDecimal thresholdValue) { this.thresholdValue = thresholdValue; }

    public BigDecimal getActualValue() { return actualValue; }
    public void setActualValue(BigDecimal actualValue) { this.actualValue = actualValue; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }

    public User getResolvedByUser() { return resolvedByUser; }
    public void setResolvedByUser(User resolvedByUser) { this.resolvedByUser = resolvedByUser; }
} 