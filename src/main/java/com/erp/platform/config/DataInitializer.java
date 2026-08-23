package com.erp.platform.config;

import com.erp.platform.model.*;
import com.erp.platform.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                       EmployeeRepository employeeRepository,
                                       InventoryRepository inventoryRepository,
                                       SaleRepository saleRepository,
                                       FinanceRepository financeRepository,
                                       FeedbackRepository feedbackRepository,
                                       PasswordEncoder encoder) {
        return args -> {

            if (userRepository.count() == 0) {
                userRepository.save(new User("admin", "admin@erp-platform.com",
                        encoder.encode("Admin@123"), "System Administrator", Role.ADMIN));
                userRepository.save(new User("manager", "manager@erp-platform.com",
                        encoder.encode("Manager@123"), "Priya Sharma", Role.MANAGER));
                userRepository.save(new User("employee", "employee@erp-platform.com",
                        encoder.encode("Employee@123"), "Ravi Kumar", Role.EMPLOYEE));
                userRepository.save(new User("customer", "customer@erp-platform.com",
                        encoder.encode("Customer@123"), "Anita Verma", Role.CUSTOMER));
            }

            if (employeeRepository.count() == 0) {
                employeeRepository.save(new Employee("Ravi Kumar", "ravi.kumar@erp-platform.com",
                        "9876543210", "Operations", "Software Engineer",
                        LocalDate.of(2022, 3, 14), new BigDecimal("65000"), "ACTIVE"));
                employeeRepository.save(new Employee("Priya Sharma", "priya.sharma@erp-platform.com",
                        "9876500011", "Sales", "Sales Manager",
                        LocalDate.of(2020, 6, 1), new BigDecimal("95000"), "ACTIVE"));
                employeeRepository.save(new Employee("Karthik Iyer", "karthik.iyer@erp-platform.com",
                        "9876511122", "Finance", "Financial Analyst",
                        LocalDate.of(2021, 11, 20), new BigDecimal("72000"), "ACTIVE"));
                employeeRepository.save(new Employee("Sneha Reddy", "sneha.reddy@erp-platform.com",
                        "9876522233", "HR", "HR Executive",
                        LocalDate.of(2023, 1, 9), new BigDecimal("48000"), "ON_LEAVE"));
            }

            if (inventoryRepository.count() == 0) {
                inventoryRepository.save(new InventoryItem("Wireless Mouse", "SKU-1001", "Electronics",
                        120, 30, new BigDecimal("499.00"), "TechSupplies Pvt Ltd"));
                inventoryRepository.save(new InventoryItem("Office Chair", "SKU-1002", "Furniture",
                        18, 20, new BigDecimal("5499.00"), "ComfortWorks"));
                inventoryRepository.save(new InventoryItem("A4 Paper Ream", "SKU-1003", "Stationery",
                        300, 50, new BigDecimal("249.00"), "PaperMart"));
                inventoryRepository.save(new InventoryItem("Laptop Stand", "SKU-1004", "Electronics",
                        8, 15, new BigDecimal("1299.00"), "TechSupplies Pvt Ltd"));
                inventoryRepository.save(new InventoryItem("Whiteboard Marker Set", "SKU-1005", "Stationery",
                        90, 25, new BigDecimal("199.00"), "PaperMart"));
            }

            if (saleRepository.count() == 0) {
                saleRepository.save(new Sale("Anita Verma", "Wireless Mouse", 2,
                        new BigDecimal("499.00"), "COMPLETED"));
                saleRepository.save(new Sale("Rajesh Nair", "Office Chair", 1,
                        new BigDecimal("5499.00"), "COMPLETED"));
                saleRepository.save(new Sale("Divya Menon", "A4 Paper Ream", 10,
                        new BigDecimal("249.00"), "PENDING"));
                saleRepository.save(new Sale("Amit Singh", "Laptop Stand", 3,
                        new BigDecimal("1299.00"), "COMPLETED"));
            }

            if (financeRepository.count() == 0) {
                financeRepository.save(new FinanceRecord("INCOME", "Product Sales",
                        new BigDecimal("258000.00"), "Quarterly product sales revenue", LocalDate.now().minusDays(10)));
                financeRepository.save(new FinanceRecord("EXPENSE", "Payroll",
                        new BigDecimal("180000.00"), "Monthly employee payroll", LocalDate.now().minusDays(5)));
                financeRepository.save(new FinanceRecord("EXPENSE", "Office Supplies",
                        new BigDecimal("12500.00"), "Stationery and consumables", LocalDate.now().minusDays(3)));
                financeRepository.save(new FinanceRecord("INCOME", "Service Contracts",
                        new BigDecimal("64000.00"), "Annual maintenance contracts", LocalDate.now().minusDays(1)));
            }

            if (feedbackRepository.count() == 0) {
                feedbackRepository.save(new Feedback("Anita Verma", "anita.verma@example.com",
                        "Wireless Mouse", 5, "Excellent build quality and very responsive. Delivery was fast too!"));
                feedbackRepository.save(new Feedback("Rajesh Nair", "rajesh.nair@example.com",
                        "Office Chair", 4, "Comfortable chair, good lumbar support. Assembly instructions could be clearer."));
                feedbackRepository.save(new Feedback("Divya Menon", "divya.menon@example.com",
                        "A4 Paper Ream", 3, "Paper quality is average for the price point."));
            }
        };
    }
}
