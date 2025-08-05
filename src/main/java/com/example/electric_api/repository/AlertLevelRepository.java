package com.example.electric_api.repository;

import com.example.electric_api.entity.AlertLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertLevelRepository extends JpaRepository<AlertLevel, Long> {
    
    List<AlertLevel> findByIsActiveTrue();
    
    Optional<AlertLevel> findByName(String name);
    
    boolean existsByName(String name);
    
    List<AlertLevel> findBySeverity(AlertLevel.Severity severity);
    
    @Query("SELECT al FROM AlertLevel al WHERE al.isActive = true AND " +
           "(al.name LIKE %:search% OR al.description LIKE %:search%)")
    List<AlertLevel> searchActiveAlertLevels(@Param("search") String search);
    
    @Query("SELECT COUNT(al) FROM AlertLevel al WHERE al.isActive = true")
    long countByIsActiveTrue();
} 