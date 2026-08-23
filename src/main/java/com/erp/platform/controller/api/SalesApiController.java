package com.erp.platform.controller.api;

import com.erp.platform.model.Sale;
import com.erp.platform.service.SalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesApiController {

    private final SalesService salesService;

    public SalesApiController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    public List<Sale> all() {
        return salesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> byId(@PathVariable Long id) {
        return salesService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody Sale sale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesService.save(sale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> update(@PathVariable Long id, @RequestBody Sale sale) {
        return salesService.findById(id).map(existing -> {
            sale.setId(id);
            return ResponseEntity.ok(salesService.save(sale));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
