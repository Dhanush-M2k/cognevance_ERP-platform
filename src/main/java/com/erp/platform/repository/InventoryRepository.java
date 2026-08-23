package com.erp.platform.repository;

import com.erp.platform.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity <= i.reorderLevel")
    List<InventoryItem> findLowStockItems();
}
