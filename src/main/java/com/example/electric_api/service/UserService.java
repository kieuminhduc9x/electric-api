package com.example.electric_api.service;

import com.example.electric_api.dto.RegisterRequest;
import com.example.electric_api.entity.LoginHistory;
import com.example.electric_api.entity.User;
import com.example.electric_api.repository.LoginHistoryRepository;
import com.example.electric_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User createUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setDepartment(request.getDepartment());
        user.setPosition(request.getPosition());
        user.setIsActive(true);

        return userRepository.save(user);
    }

    public User updateUser(Long userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setDepartment(request.getDepartment());
        user.setPosition(request.getPosition());

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setIsActive(false);
        userRepository.save(user);
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }
    
    // NEW METHODS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    public List<LoginHistory> getUserLoginHistory(Long userId) {
        return loginHistoryRepository.findByUserIdOrderByLoginTimeDesc(userId);
    }
    
    public long getTotalUsersCount() {
        return userRepository.count();
    }
    
    public long getActiveUsersCount() {
        return userRepository.findByIsActiveTrue().size();
    }

    public void logLoginHistory(User user, String ipAddress, String location, 
                               String device, String browser, boolean success) {
        LoginHistory loginHistory = new LoginHistory(user, ipAddress, location, device, browser, success);
        loginHistoryRepository.save(loginHistory);
    }

    public List<User> searchUsers(String search) {
        return userRepository.searchActiveUsers(search);
    }

    public long countUsersByRole(User.Role role) {
        return userRepository.countByRoleAndIsActiveTrue(role);
    }
} 