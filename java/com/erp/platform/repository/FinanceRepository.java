package com.erp.platform.repository;

import com.erp.platform.model.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<FinanceRecord, Long> {
    List<FinanceRecord> findByType(String type);
}
