package com.erp.platform.service;

import com.erp.platform.model.InventoryItem;
import com.erp.platform.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryItem> findAll() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryItem> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    public InventoryItem save(InventoryItem item) {
        item.setLastUpdated(LocalDateTime.now());
        return inventoryRepository.save(item);
    }

    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    public List<InventoryItem> findLowStock() {
        return inventoryRepository.findLowStockItems();
    }

    public long count() {
        return inventoryRepository.count();
    }
}
