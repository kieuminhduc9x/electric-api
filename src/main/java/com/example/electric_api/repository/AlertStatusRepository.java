package com.example.electric_api.repository;

import com.example.electric_api.entity.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertStatusRepository extends JpaRepository<AlertStatus, Long> {
    
    List<AlertStatus> findByIsActiveTrue();
    
    Optional<AlertStatus> findByName(String name);
    
    boolean existsByName(String name);
    
    List<AlertStatus> findByPriority(Integer priority);
    
    @Query("SELECT asts FROM AlertStatus asts WHERE asts.isActive = true AND " +
           "(asts.name LIKE %:search% OR asts.description LIKE %:search%)")
    List<AlertStatus> searchActiveAlertStatuses(@Param("search") String search);
    
    @Query("SELECT COUNT(asts) FROM AlertStatus asts WHERE asts.isActive = true")
    long countByIsActiveTrue();
    
    @Query("SELECT asts FROM AlertStatus asts WHERE asts.isActive = true ORDER BY asts.priority ASC")
    List<AlertStatus> findAllByPriorityOrder();
} 