package com.example.electric_api.repository;

import com.example.electric_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByIsActiveTrue();
    
    List<User> findByRole(User.Role role);
    
    @Query("SELECT u FROM User u WHERE u.isActive = true AND " +
           "(u.name LIKE %:search% OR u.email LIKE %:search% OR u.department LIKE %:search%)")
    List<User> searchActiveUsers(@Param("search") String search);
    
    long countByRoleAndIsActiveTrue(User.Role role);
} 