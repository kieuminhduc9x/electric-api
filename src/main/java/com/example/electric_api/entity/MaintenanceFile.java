package com.example.electric_api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_files")
public class MaintenanceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_record_id", nullable = false)
    private MaintenanceRecord maintenanceRecord;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    private Long fileSize;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt;

    // Enum for file types
    public enum FileType {
        PHOTO, DOCUMENT, VIDEO, OTHER
    }

    // Constructors
    public MaintenanceFile() {}

    public MaintenanceFile(MaintenanceRecord maintenanceRecord, FileType fileType, 
                          String fileName, String filePath) {
        this.maintenanceRecord = maintenanceRecord;
        this.fileType = fileType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MaintenanceRecord getMaintenanceRecord() { return maintenanceRecord; }
    public void setMaintenanceRecord(MaintenanceRecord maintenanceRecord) { this.maintenanceRecord = maintenanceRecord; }

    public FileType getFileType() { return fileType; }
    public void setFileType(FileType fileType) { this.fileType = fileType; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
} 