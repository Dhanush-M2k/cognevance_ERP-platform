package com.erp.platform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 150)
    private String itemName;

    @Column(length = 50, unique = true)
    private String sku;

    @Column(length = 100)
    private String category;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "unit_price", precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(length = 150)
    private String supplier;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public InventoryItem() {
        this.lastUpdated = LocalDateTime.now();
    }

    public InventoryItem(String itemName, String sku, String category, Integer quantity,
                          Integer reorderLevel, BigDecimal unitPrice, String supplier) {
        this.itemName = itemName;
        this.sku = sku;
        this.category = category;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.unitPrice = unitPrice;
        this.supplier = supplier;
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /** Convenience method used by dashboard/templates to flag low-stock items. */
    public boolean isLowStock() {
        if (reorderLevel == null || quantity == null) {
            return false;
        }
        return quantity <= reorderLevel;
    }
}
