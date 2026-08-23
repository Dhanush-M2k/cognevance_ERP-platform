package com.erp.platform.repository;

import com.erp.platform.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByStatus(String status);

    List<Purchase> findBySupplierNameContainingIgnoreCase(String supplierName);
}
