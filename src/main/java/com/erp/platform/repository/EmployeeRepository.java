package com.erp.platform.repository;

import com.erp.platform.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    long countByStatus(String status);
}
