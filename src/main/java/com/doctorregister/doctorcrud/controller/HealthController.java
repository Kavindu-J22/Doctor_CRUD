package com.doctorregister.doctorcrud.controller;

import com.doctorregister.doctorcrud.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Controller
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/health")
    @ResponseBody
    public String healthCheck() {
        StringBuilder status = new StringBuilder();
        status.append("Doctor Registration System Health Check\n");
        status.append("=====================================\n\n");

        // Test database connection
        try (Connection connection = dataSource.getConnection()) {
            status.append("✅ Database Connection: SUCCESS\n");
            status.append("   Database URL: ").append(connection.getMetaData().getURL()).append("\n");
            status.append("   Database Product: ").append(connection.getMetaData().getDatabaseProductName()).append("\n");
            status.append("   Database Version: ").append(connection.getMetaData().getDatabaseProductVersion()).append("\n");
        } catch (SQLException e) {
            status.append("❌ Database Connection: FAILED\n");
            status.append("   Error: ").append(e.getMessage()).append("\n");
        }

        // Test service layer
        try {
            long doctorCount = doctorService.getTotalDoctorCount();
            status.append("✅ Service Layer: SUCCESS\n");
            status.append("   Total Doctors: ").append(doctorCount).append("\n");
        } catch (Exception e) {
            status.append("❌ Service Layer: FAILED\n");
            status.append("   Error: ").append(e.getMessage()).append("\n");
        }

        status.append("\nIf you see any failures above, please:\n");
        status.append("1. Ensure MySQL is running\n");
        status.append("2. Check database credentials in application.properties\n");
        status.append("3. Verify database 'doctor_registration' exists or can be created\n");

        return status.toString();
    }

    @GetMapping("/test-db")
    public String testDatabase(Model model) {
        try (Connection connection = dataSource.getConnection()) {
            model.addAttribute("successMessage", "Database connection successful!");
            model.addAttribute("dbUrl", connection.getMetaData().getURL());
            model.addAttribute("dbProduct", connection.getMetaData().getDatabaseProductName());
            model.addAttribute("dbVersion", connection.getMetaData().getDatabaseProductVersion());
            
            long doctorCount = doctorService.getTotalDoctorCount();
            model.addAttribute("doctorCount", doctorCount);
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Database connection failed: " + e.getMessage());
            model.addAttribute("errorDetails", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        
        return "test-db";
    }
}
