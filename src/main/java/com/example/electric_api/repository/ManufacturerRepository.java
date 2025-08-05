package com.example.electric_api.repository;

import com.example.electric_api.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    
    List<Manufacturer> findByIsActiveTrue();
    
    Optional<Manufacturer> findByName(String name);
    
    boolean existsByName(String name);
    
    List<Manufacturer> findByCountry(String country);
    
    @Query("SELECT m FROM Manufacturer m WHERE m.isActive = true AND " +
           "(m.name LIKE %:search% OR m.country LIKE %:search% OR m.description LIKE %:search%)")
    List<Manufacturer> searchActiveManufacturers(@Param("search") String search);
    
    @Query("SELECT COUNT(m) FROM Manufacturer m WHERE m.isActive = true")
    long countByIsActiveTrue();
} 