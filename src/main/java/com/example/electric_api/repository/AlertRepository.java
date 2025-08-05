package com.example.electric_api.repository;

import com.example.electric_api.entity.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    
    List<Alert> findByDeviceId(Long deviceId);
    
    Page<Alert> findByDeviceId(Long deviceId, Pageable pageable);
    
    List<Alert> findByAlertType(Alert.AlertType alertType);
    
    Page<Alert> findByAlertType(Alert.AlertType alertType, Pageable pageable);
    
    @Query("SELECT a FROM Alert a WHERE a.resolvedAt IS NULL")
    List<Alert> findUnresolvedAlerts();
    
    @Query("SELECT a FROM Alert a WHERE a.resolvedAt IS NULL")
    Page<Alert> findUnresolvedAlerts(Pageable pageable);
    
    @Query("SELECT a FROM Alert a WHERE a.resolvedAt IS NOT NULL")
    List<Alert> findResolvedAlerts();
    
    @Query("SELECT a FROM Alert a WHERE a.resolvedAt IS NOT NULL")
    Page<Alert> findResolvedAlerts(Pageable pageable);
    
    @Query("SELECT a FROM Alert a WHERE a.alertLevel.severity = 'CRITICAL' AND a.resolvedAt IS NULL")
    List<Alert> findCriticalAlerts();
    
    @Query("SELECT a FROM Alert a WHERE a.createdAt BETWEEN :startDate AND :endDate")
    List<Alert> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.resolvedAt IS NULL")
    long countUnresolvedAlerts();
    
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.resolvedAt IS NOT NULL")
    long countResolvedAlerts();
    
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.alertLevel.severity = 'CRITICAL' AND a.resolvedAt IS NULL")
    long countCriticalAlerts();
    
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.device.id = :deviceId")
    long countByDeviceId(@Param("deviceId") Long deviceId);
} 