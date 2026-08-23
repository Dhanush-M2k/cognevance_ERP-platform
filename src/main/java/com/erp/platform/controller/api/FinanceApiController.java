package com.erp.platform.controller.api;

import com.erp.platform.model.FinanceRecord;
import com.erp.platform.service.FinanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinanceApiController {

    private final FinanceService financeService;

    public FinanceApiController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public List<FinanceRecord> all() {
        return financeService.findAll();
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return Map.of(
                "totalIncome", financeService.totalIncome(),
                "totalExpense", financeService.totalExpense(),
                "netProfit", financeService.netProfit()
        );
    }

    @PostMapping
    public ResponseEntity<FinanceRecord> create(@RequestBody FinanceRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeService.save(record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        financeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
