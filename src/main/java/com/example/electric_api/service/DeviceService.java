package com.example.electric_api.service;

import com.example.electric_api.controller.DeviceController;
import com.example.electric_api.entity.*;
import com.example.electric_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private StationRepository stationRepository;

    public Page<Device> getAllDevices(Pageable pageable) {
        return deviceRepository.findByIsActiveTrue(pageable);
    }

    public Page<Device> searchDevices(String search, Pageable pageable) {
        return deviceRepository.searchActiveDevices(search, pageable);
    }

    public Page<Device> getDevicesByStatus(Device.DeviceStatus status, Pageable pageable) {
        return deviceRepository.findByStatusAndIsActiveTrue(status, pageable);
    }

    public Page<Device> getDevicesByStation(Long stationId, Pageable pageable) {
        return deviceRepository.findByStationIdAndIsActiveTrue(stationId, pageable);
    }

    public Page<Device> getDevicesByType(Long deviceTypeId, Pageable pageable) {
        return deviceRepository.findByDeviceTypeIdAndIsActiveTrue(deviceTypeId, pageable);
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findByIdAndIsActiveTrue(id)
            .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }

    public Device createDevice(DeviceController.DeviceCreateRequest request) {
        // Validate required entities exist
        DeviceType deviceType = deviceTypeRepository.findById(request.getDeviceTypeId())
            .orElseThrow(() -> new RuntimeException("Device type not found"));
        
        Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturerId())
            .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
            
        Station station = stationRepository.findById(request.getStationId())
            .orElseThrow(() -> new RuntimeException("Station not found"));

        // Check for duplicate serial number
        if (request.getSerialNumber() != null && 
            deviceRepository.existsBySerialNumber(request.getSerialNumber())) {
            throw new RuntimeException("Device with serial number already exists: " + request.getSerialNumber());
        }

        Device device = new Device();
        device.setDeviceType(deviceType);
        device.setManufacturer(manufacturer);
        device.setStation(station);
        device.setName(request.getName());
        device.setModel(request.getModel());
        device.setSerialNumber(request.getSerialNumber());
        device.setStatus(Device.DeviceStatus.OFFLINE); // Default status
        device.setSpecifications(request.getSpecifications());
        device.setIsActive(true);

        if (request.getInstallationDate() != null) {
            device.setInstallationDate(LocalDate.parse(request.getInstallationDate()));
        }

        return deviceRepository.save(device);
    }

    public Device updateDevice(Long id, DeviceController.DeviceUpdateRequest request) {
        Device device = getDeviceById(id);

        // Update basic info
        if (request.getName() != null) device.setName(request.getName());
        if (request.getModel() != null) device.setModel(request.getModel());
        if (request.getSpecifications() != null) device.setSpecifications(request.getSpecifications());

        // Update serial number if changed and not duplicate
        if (request.getSerialNumber() != null && 
            !request.getSerialNumber().equals(device.getSerialNumber())) {
            if (deviceRepository.existsBySerialNumber(request.getSerialNumber())) {
                throw new RuntimeException("Device with serial number already exists: " + request.getSerialNumber());
            }
            device.setSerialNumber(request.getSerialNumber());
        }

        // Update status
        if (request.getStatus() != null) {
            device.setStatus(Device.DeviceStatus.valueOf(request.getStatus().toUpperCase()));
        }

        // Update dates
        if (request.getInstallationDate() != null) {
            device.setInstallationDate(LocalDate.parse(request.getInstallationDate()));
        }
        if (request.getLastMaintenance() != null) {
            device.setLastMaintenance(LocalDate.parse(request.getLastMaintenance()));
        }

        // Update relationships if provided
        if (request.getDeviceTypeId() != null && !request.getDeviceTypeId().equals(device.getDeviceType().getId())) {
            DeviceType deviceType = deviceTypeRepository.findById(request.getDeviceTypeId())
                .orElseThrow(() -> new RuntimeException("Device type not found"));
            device.setDeviceType(deviceType);
        }

        if (request.getManufacturerId() != null && !request.getManufacturerId().equals(device.getManufacturer().getId())) {
            Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturerId())
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
            device.setManufacturer(manufacturer);
        }

        if (request.getStationId() != null && !request.getStationId().equals(device.getStation().getId())) {
            Station station = stationRepository.findById(request.getStationId())
                .orElseThrow(() -> new RuntimeException("Station not found"));
            device.setStation(station);
        }

        return deviceRepository.save(device);
    }

    public void deleteDevice(Long id) {
        Device device = getDeviceById(id);
        device.setIsActive(false);
        deviceRepository.save(device);
    }

    public Map<String, Object> getDeviceData(Long deviceId, Pageable pageable) {
        Device device = getDeviceById(deviceId);
        
        // Generate mock real-time data since we don't have real sensors
        List<Map<String, Object>> mockData = generateMockDeviceData(device, 20);
        
        Map<String, Object> response = new HashMap<>();
        response.put("device", device);
        response.put("data", mockData);
        response.put("lastUpdated", LocalDateTime.now());
        response.put("status", device.getStatus());
        
        return response;
    }

    public Map<String, Object> getDeviceDataHistory(Long deviceId, String startDate, String endDate, Pageable pageable) {
        Device device = getDeviceById(deviceId);
        
        // Generate mock historical data
        List<Map<String, Object>> mockHistory = generateMockHistoricalData(device, startDate, endDate);
        
        Map<String, Object> response = new HashMap<>();
        response.put("device", device);
        response.put("history", mockHistory);
        response.put("totalRecords", mockHistory.size());
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        
        return response;
    }

    public Map<String, Object> getDeviceStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Count by status
        Map<String, Long> statusCounts = new HashMap<>();
        for (Device.DeviceStatus status : Device.DeviceStatus.values()) {
            statusCounts.put(status.name().toLowerCase(), 
                deviceRepository.countByStatusAndIsActiveTrue(status));
        }
        
        // Count by station
        List<Object[]> stationCounts = stationRepository.findAll().stream()
            .map(station -> new Object[]{
                station.getName(), 
                deviceRepository.countByStationIdAndIsActiveTrue(station.getId())
            })
            .collect(Collectors.toList());
        
        // Count by device type
        List<Object[]> typeCounts = deviceTypeRepository.findAll().stream()
            .map(type -> new Object[]{
                type.getName(),
                deviceRepository.countByDeviceTypeIdAndIsActiveTrue(type.getId())
            })
            .collect(Collectors.toList());
        
        stats.put("totalDevices", deviceRepository.countByIsActiveTrue());
        stats.put("statusDistribution", statusCounts);
        stats.put("stationDistribution", stationCounts);
        stats.put("typeDistribution", typeCounts);
        stats.put("lastUpdated", LocalDateTime.now());
        
        return stats;
    }

    // Mock data generators
    private List<Map<String, Object>> generateMockDeviceData(Device device, int count) {
        List<Map<String, Object>> data = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < count; i++) {
            Map<String, Object> reading = new HashMap<>();
            reading.put("timestamp", LocalDateTime.now().minusMinutes(i * 5));
            reading.put("voltage", BigDecimal.valueOf(220 + random.nextGaussian() * 10));
            reading.put("current", BigDecimal.valueOf(30 + random.nextGaussian() * 5));
            reading.put("power", BigDecimal.valueOf(6600 + random.nextGaussian() * 500));
            reading.put("frequency", BigDecimal.valueOf(50 + random.nextGaussian() * 0.5));
            reading.put("temperature", BigDecimal.valueOf(25 + random.nextGaussian() * 10));
            data.add(reading);
        }
        
        return data;
    }

    private List<Map<String, Object>> generateMockHistoricalData(Device device, String startDate, String endDate) {
        List<Map<String, Object>> history = new ArrayList<>();
        Random random = new Random();
        
        LocalDateTime start = startDate != null ? 
            LocalDate.parse(startDate).atStartOfDay() : 
            LocalDateTime.now().minusDays(7);
        LocalDateTime end = endDate != null ? 
            LocalDate.parse(endDate).atTime(23, 59, 59) : 
            LocalDateTime.now();
        
        LocalDateTime current = start;
        while (current.isBefore(end)) {
            Map<String, Object> reading = new HashMap<>();
            reading.put("timestamp", current);
            reading.put("voltage", BigDecimal.valueOf(220 + random.nextGaussian() * 10));
            reading.put("current", BigDecimal.valueOf(30 + random.nextGaussian() * 5));
            reading.put("power", BigDecimal.valueOf(6600 + random.nextGaussian() * 500));
            reading.put("frequency", BigDecimal.valueOf(50 + random.nextGaussian() * 0.5));
            reading.put("temperature", BigDecimal.valueOf(25 + random.nextGaussian() * 10));
            history.add(reading);
            
            current = current.plusHours(1); // Hourly data
        }
        
        return history;
    }
} 