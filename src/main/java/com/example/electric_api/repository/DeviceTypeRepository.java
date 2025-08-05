package com.example.electric_api.repository;

import com.example.electric_api.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    
    List<DeviceType> findByIsActiveTrue();
    
    Optional<DeviceType> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT dt FROM DeviceType dt WHERE dt.isActive = true AND " +
           "(dt.name LIKE %:search% OR dt.description LIKE %:search%)")
    List<DeviceType> searchActiveDeviceTypes(@Param("search") String search);
    
    @Query("SELECT COUNT(dt) FROM DeviceType dt WHERE dt.isActive = true")
    long countByIsActiveTrue();
} 