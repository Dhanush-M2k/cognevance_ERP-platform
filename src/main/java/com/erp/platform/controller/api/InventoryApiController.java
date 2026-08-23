package com.erp.platform.controller.api;

import com.erp.platform.model.InventoryItem;
import com.erp.platform.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryApiController {

    private final InventoryService inventoryService;

    public InventoryApiController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryItem> all() {
        return inventoryService.findAll();
    }

    @GetMapping("/low-stock")
    public List<InventoryItem> lowStock() {
        return inventoryService.findLowStock();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> byId(@PathVariable Long id) {
        return inventoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryItem> create(@RequestBody InventoryItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> update(@PathVariable Long id, @RequestBody InventoryItem item) {
        return inventoryService.findById(id).map(existing -> {
            item.setId(id);
            return ResponseEntity.ok(inventoryService.save(item));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
