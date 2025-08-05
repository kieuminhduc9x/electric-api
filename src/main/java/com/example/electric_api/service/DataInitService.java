package com.example.electric_api.service;

import com.example.electric_api.entity.User;
import com.example.electric_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitService implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Kiểm tra nếu đã có data thì không tạo lại
        if (userRepository.count() > 0) {
            System.out.println("✅ Data already exists, skipping initialization");
            return;
        }

        System.out.println("🚀 Initializing demo data...");

        // Tạo demo users
        createDemoUsers();

        System.out.println("✅ Demo data initialized successfully!");
        System.out.println("🔑 Demo Accounts:");
        System.out.println("   Admin: admin@powergrid.com / admin123");
        System.out.println("   Operator: operator@powergrid.com / operator123");
        System.out.println("   User: user@powergrid.com / user123");
        System.out.println("   Viewer: viewer@powergrid.com / viewer123");
    }

    private void createDemoUsers() {
        try {
            // Admin User
            User admin = new User();
            admin.setEmail("admin@powergrid.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setName("System Administrator");
            admin.setRole(User.Role.ADMIN);
            admin.setDepartment("IT Department");
            admin.setPosition("System Administrator");
            admin.setPhone("+84-901-234-567");
            admin.setIsActive(true);
            userRepository.save(admin);

            // Operator User
            User operator = new User();
            operator.setEmail("operator@powergrid.com");
            operator.setPassword(passwordEncoder.encode("operator123"));
            operator.setName("Grid Operator");
            operator.setRole(User.Role.OPERATOR);
            operator.setDepartment("Operations");
            operator.setPosition("Senior Operator");
            operator.setPhone("+84-901-234-568");
            operator.setIsActive(true);
            userRepository.save(operator);

            // Regular User
            User user = new User();
            user.setEmail("user@powergrid.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setName("Regular User");
            user.setRole(User.Role.USER);
            user.setDepartment("Engineering");
            user.setPosition("Electrical Engineer");
            user.setPhone("+84-901-234-569");
            user.setIsActive(true);
            userRepository.save(user);

            // Viewer User
            User viewer = new User();
            viewer.setEmail("viewer@powergrid.com");
            viewer.setPassword(passwordEncoder.encode("viewer123"));
            viewer.setName("Data Viewer");
            viewer.setRole(User.Role.VIEWER);
            viewer.setDepartment("Management");
            viewer.setPosition("Supervisor");
            viewer.setPhone("+84-901-234-570");
            viewer.setIsActive(true);
            userRepository.save(viewer);

            System.out.println("✅ Created 4 demo users successfully");

        } catch (Exception e) {
            System.err.println("❌ Error creating demo users: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 