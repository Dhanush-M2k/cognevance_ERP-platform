package com.erp.platform.service;

import com.erp.platform.model.Purchase;
import com.erp.platform.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    public Purchase createPurchase(Purchase purchase) {
        if (purchase.getPurchaseDate() == null) {
            purchase.setPurchaseDate(LocalDate.now());
        }
        if (purchase.getStatus() == null || purchase.getStatus().isBlank()) {
            purchase.setStatus(Purchase.STATUS_PENDING);
        }
        purchase.setTotalCost(purchase.getQuantity() * purchase.getUnitPrice());
        return purchaseRepository.save(purchase);
    }

    public Optional<Purchase> updatePurchase(Long id, Purchase updated) {
        return purchaseRepository.findById(id).map(existing -> {
            existing.setSupplierName(updated.getSupplierName());
            existing.setItemName(updated.getItemName());
            existing.setQuantity(updated.getQuantity());
            existing.setUnitPrice(updated.getUnitPrice());
            existing.setTotalCost(updated.getQuantity() * updated.getUnitPrice());
            if (updated.getPurchaseDate() != null) {
                existing.setPurchaseDate(updated.getPurchaseDate());
            }
            if (updated.getStatus() != null && !updated.getStatus().isBlank()) {
                existing.setStatus(updated.getStatus());
            }
            return purchaseRepository.save(existing);
        });
    }

    public boolean deletePurchase(Long id) {
        if (purchaseRepository.existsById(id)) {
            purchaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public double getTotalPurchaseCost() {
        double total = 0.0;
        for (Purchase purchase : purchaseRepository.findAll()) {
            total += purchase.getTotalCost();
        }
        return total;
    }

    public List<Purchase> getPendingPurchases() {
        return purchaseRepository.findByStatus(Purchase.STATUS_PENDING);
    }
}
