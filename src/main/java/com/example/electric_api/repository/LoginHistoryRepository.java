package com.example.electric_api.repository;

import com.example.electric_api.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    
    List<LoginHistory> findByUserIdOrderByLoginTimeDesc(Long userId);
    
    List<LoginHistory> findByUserIdAndSuccessTrue(Long userId);
    
    List<LoginHistory> findByUserIdAndLoginTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT lh FROM LoginHistory lh WHERE lh.user.id = :userId AND lh.success = true ORDER BY lh.loginTime DESC")
    List<LoginHistory> findSuccessfulLoginsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(lh) FROM LoginHistory lh WHERE lh.user.id = :userId AND lh.success = true")
    long countSuccessfulLoginsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(lh) FROM LoginHistory lh WHERE lh.user.id = :userId AND lh.success = false")
    long countFailedLoginsByUserId(@Param("userId") Long userId);
} 