package com.example.electric_api.repository;

import com.example.electric_api.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    List<Device> findByIsActiveTrue();
    
    Page<Device> findByIsActiveTrue(Pageable pageable);
    
    Page<Device> findByStatusAndIsActiveTrue(Device.DeviceStatus status, Pageable pageable);
    
    Page<Device> findByStationIdAndIsActiveTrue(Long stationId, Pageable pageable);
    
    Page<Device> findByDeviceTypeIdAndIsActiveTrue(Long deviceTypeId, Pageable pageable);
    
    Optional<Device> findByIdAndIsActiveTrue(Long id);
    
    List<Device> findByStatus(Device.DeviceStatus status);
    
    List<Device> findByStationId(Long stationId);
    
    List<Device> findByDeviceTypeId(Long deviceTypeId);
    
    List<Device> findByManufacturerId(Long manufacturerId);
    
    Optional<Device> findBySerialNumber(String serialNumber);
    
    boolean existsBySerialNumber(String serialNumber);
    
    @Query("SELECT d FROM Device d WHERE d.isActive = true AND " +
           "(d.name LIKE %:search% OR d.model LIKE %:search% OR d.serialNumber LIKE %:search%)")
    Page<Device> searchActiveDevices(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.status = :status AND d.isActive = true")
    long countByStatusAndIsActiveTrue(@Param("status") Device.DeviceStatus status);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.isActive = true")
    long countByIsActiveTrue();
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.station.id = :stationId AND d.isActive = true")
    long countByStationIdAndIsActiveTrue(@Param("stationId") Long stationId);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.deviceType.id = :deviceTypeId AND d.isActive = true")
    long countByDeviceTypeIdAndIsActiveTrue(@Param("deviceTypeId") Long deviceTypeId);
    
    @Query("SELECT d FROM Device d JOIN d.station s WHERE s.name = :stationName AND d.isActive = true")
    List<Device> findByStationName(@Param("stationName") String stationName);
} 