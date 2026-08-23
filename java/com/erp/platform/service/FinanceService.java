package com.erp.platform.service;

import com.erp.platform.model.FinanceRecord;
import com.erp.platform.repository.FinanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FinanceService {

    private final FinanceRepository financeRepository;

    public FinanceService(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    public List<FinanceRecord> findAll() {
        return financeRepository.findAll();
    }

    public Optional<FinanceRecord> findById(Long id) {
        return financeRepository.findById(id);
    }

    public FinanceRecord save(FinanceRecord record) {
        return financeRepository.save(record);
    }

    public void deleteById(Long id) {
        financeRepository.deleteById(id);
    }

    public BigDecimal totalIncome() {
        return financeRepository.findByType("INCOME").stream()
                .map(FinanceRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalExpense() {
        return financeRepository.findByType("EXPENSE").stream()
                .map(FinanceRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal netProfit() {
        return totalIncome().subtract(totalExpense());
    }
}
