package com.erp.platform.service;

import com.erp.platform.model.Sale;
import com.erp.platform.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SalesService {

    private final SaleRepository saleRepository;

    public SalesService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public List<Sale> findRecent() {
        return saleRepository.findTop10ByOrderBySaleDateDesc();
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale save(Sale sale) {
        sale.recalculateTotal();
        return saleRepository.save(sale);
    }

    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

    public long count() {
        return saleRepository.count();
    }

    public BigDecimal totalRevenue() {
        return saleRepository.findAll().stream()
                .filter(s -> "COMPLETED".equals(s.getStatus()))
                .map(Sale::getTotalAmount)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
