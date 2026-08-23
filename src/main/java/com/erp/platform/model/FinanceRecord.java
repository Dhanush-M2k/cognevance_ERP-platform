package com.erp.platform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "finance_records")
public class FinanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String type; // INCOME or EXPENSE

    @Column(length = 100)
    private String category;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(length = 255)
    private String description;

    @Column(name = "record_date")
    private LocalDate recordDate;

    public FinanceRecord() {
        this.recordDate = LocalDate.now();
    }

    public FinanceRecord(String type, String category, BigDecimal amount,
                          String description, LocalDate recordDate) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.recordDate = recordDate != null ? recordDate : LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
}
