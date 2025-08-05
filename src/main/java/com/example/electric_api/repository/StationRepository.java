package com.example.electric_api.repository;

import com.example.electric_api.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    
    List<Station> findByIsActiveTrue();
    
    Optional<Station> findByName(String name);
    
    boolean existsByName(String name);
    
    List<Station> findByLocation(String location);
    
    @Query("SELECT s FROM Station s WHERE s.isActive = true AND " +
           "(s.name LIKE %:search% OR s.location LIKE %:search% OR s.address LIKE %:search%)")
    List<Station> searchActiveStations(@Param("search") String search);
    
    @Query("SELECT COUNT(s) FROM Station s WHERE s.isActive = true")
    long countByIsActiveTrue();
    
    @Query("SELECT s FROM Station s WHERE s.isActive = true AND " +
           "s.latitude BETWEEN :minLat AND :maxLat AND s.longitude BETWEEN :minLng AND :maxLng")
    List<Station> findStationsInArea(@Param("minLat") java.math.BigDecimal minLat, 
                                   @Param("maxLat") java.math.BigDecimal maxLat,
                                   @Param("minLng") java.math.BigDecimal minLng, 
                                   @Param("maxLng") java.math.BigDecimal maxLng);
} 