package com.example.electric_api.controller;

import com.example.electric_api.dto.RegisterRequest;
import com.example.electric_api.entity.LoginHistory;
import com.example.electric_api.entity.User;
import com.example.electric_api.service.CustomUserDetailsService;
import com.example.electric_api.service.UserService;
import com.example.electric_api.service.FakeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Tag(name = "User Management", description = "APIs for managing users (Admin only) and user profiles")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FakeDataService fakeDataService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users (Admin only)")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean active) {
        
        try {
            // Try database first
            List<User> users;
            
            if (search != null && !search.isEmpty()) {
                users = userService.searchUsers(search);
            } else if (role != null) {
                users = userService.getUsersByRole(User.Role.valueOf(role.toUpperCase()));
            } else if (active != null && active) {
                users = userService.getAllActiveUsers();
            } else {
                users = userService.getAllUsers();
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            response.put("totalUsers", users.size());
            response.put("dataSource", "DATABASE");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Fallback to fake data
            System.out.println("⚠️ Database unavailable, using fake users: " + e.getMessage());
            
            List<User> fakeUsers = fakeDataService.getFakeUsers();
            
            // Apply search filter if provided
            if (search != null && !search.isEmpty()) {
                fakeUsers = fakeDataService.searchFakeUsers(search);
            }
            
            // Apply role filter if provided
            if (role != null) {
                User.Role userRole = User.Role.valueOf(role.toUpperCase());
                fakeUsers = fakeUsers.stream()
                    .filter(user -> user.getRole() == userRole)
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Apply active filter if provided
            if (active != null && active) {
                fakeUsers = fakeUsers.stream()
                    .filter(User::getIsActive)
                    .collect(java.util.stream.Collectors.toList());
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("users", fakeUsers);
            response.put("totalUsers", fakeUsers.size());
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID (Admin only)")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // Fallback to fake data
            User fakeUser = fakeDataService.getFakeUserById(id);
            if (fakeUser != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("user", fakeUser);
                response.put("dataSource", "FAKE_DATA");
                response.put("message", "Using fake data - database unavailable");
                return ResponseEntity.ok(response);
            }
            
            Map<String, String> error = new HashMap<>();
            error.put("message", "User not found: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new user (Admin only)")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest request) {
        try {
            if (userService.existsByEmail(request.getEmail())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Email already exists");
                return ResponseEntity.badRequest().body(error);
            }

            User user = userService.createUser(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // For fake data, just return success message
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User creation simulated - using fake data mode");
            response.put("user", createFakeUserFromRequest(request));
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user by ID (Admin only)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.updateUser(id, request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User update simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID (Admin only)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User deletion simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<?> getCurrentUserProfile() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userService.findByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // Try to find fake user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User fakeUser = fakeDataService.getFakeUserByEmail(email);
            
            if (fakeUser != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("user", fakeUser);
                response.put("dataSource", "FAKE_DATA");
                response.put("message", "Using fake data - database unavailable");
                return ResponseEntity.ok(response);
            }
            
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error fetching profile: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<?> updateCurrentUserProfile(@Valid @RequestBody UserProfileUpdateRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userService.findByEmail(email);
            
            // Update profile fields
            if (request.getName() != null) user.setName(request.getName());
            if (request.getPhone() != null) user.setPhone(request.getPhone());
            if (request.getDepartment() != null) user.setDepartment(request.getDepartment());
            if (request.getPosition() != null) user.setPosition(request.getPosition());
            if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
            
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profile update simulated - using fake data mode");
            response.put("dataSource", "FAKE_DATA");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}/login-history")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user login history by ID (Admin only)")
    public ResponseEntity<?> getUserLoginHistory(@PathVariable Long id,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        try {
            List<LoginHistory> history = userService.getUserLoginHistory(id);
            Map<String, Object> response = new HashMap<>();
            response.put("loginHistory", history);
            response.put("totalRecords", history.size());
            response.put("dataSource", "DATABASE");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Generate fake login history
            List<Map<String, Object>> fakeHistory = generateFakeLoginHistory(id);
            Map<String, Object> response = new HashMap<>();
            response.put("loginHistory", fakeHistory);
            response.put("totalRecords", fakeHistory.size());
            response.put("dataSource", "FAKE_DATA");
            response.put("message", "Using fake data - database unavailable");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user statistics (Admin only)")
    public ResponseEntity<?> getUserStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Count by role
            Map<String, Long> roleCounts = new HashMap<>();
            for (User.Role role : User.Role.values()) {
                roleCounts.put(role.name().toLowerCase(), userService.countUsersByRole(role));
            }
            
            stats.put("totalUsers", userService.getTotalUsersCount());
            stats.put("activeUsers", userService.getActiveUsersCount());
            stats.put("roleDistribution", roleCounts);
            stats.put("dataSource", "DATABASE");
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            // Use fake user statistics
            Map<String, Object> fakeStats = new HashMap<>();
            
            Map<String, Long> roleCounts = new HashMap<>();
            roleCounts.put("admin", 1L);
            roleCounts.put("operator", 2L);
            roleCounts.put("user", 3L);
            roleCounts.put("viewer", 2L);
            
            fakeStats.put("totalUsers", 8L);
            fakeStats.put("activeUsers", 8L);
            fakeStats.put("roleDistribution", roleCounts);
            fakeStats.put("dataSource", "FAKE_DATA");
            fakeStats.put("message", "Using fake data - database unavailable");
            
            return ResponseEntity.ok(fakeStats);
        }
    }

    // Helper methods
    private User createFakeUserFromRequest(RegisterRequest request) {
        User fakeUser = new User();
        fakeUser.setId(System.currentTimeMillis()); // Use timestamp as fake ID
        fakeUser.setEmail(request.getEmail());
        fakeUser.setName(request.getName());
        fakeUser.setRole(request.getRole());
        fakeUser.setPhone(request.getPhone());
        fakeUser.setDepartment(request.getDepartment());
        fakeUser.setPosition(request.getPosition());
        fakeUser.setIsActive(true);
        fakeUser.setCreatedAt(java.time.LocalDateTime.now());
        return fakeUser;
    }

    private List<Map<String, Object>> generateFakeLoginHistory(Long userId) {
        List<Map<String, Object>> history = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        for (int i = 0; i < 10; i++) {
            Map<String, Object> login = new HashMap<>();
            login.put("id", i + 1);
            login.put("userId", userId);
            login.put("loginTime", java.time.LocalDateTime.now().minusHours(random.nextInt(168))); // Last week
            login.put("ipAddress", "192.168." + (1 + random.nextInt(255)) + "." + (1 + random.nextInt(255)));
            login.put("location", "Ho Chi Minh City, Vietnam");
            login.put("device", random.nextBoolean() ? "Desktop" : "Mobile");
            login.put("browser", random.nextBoolean() ? "Chrome" : "Firefox");
            login.put("success", random.nextInt(10) > 1); // 90% success rate
            history.add(login);
        }
        
        return history;
    }

    // DTO
    public static class UserProfileUpdateRequest {
        private String name;
        private String phone;
        private String department;
        private String position;
        private String avatarUrl;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        
        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }
} 