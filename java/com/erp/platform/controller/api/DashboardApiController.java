package com.erp.platform.controller.api;

import com.erp.platform.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {

    private final DashboardService dashboardService;

    public DashboardApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return dashboardService.summary();
    }

    @GetMapping("/revenue-series")
    public Map<String, Object> revenueSeries() {
        return dashboardService.revenueSeries();
    }

    @GetMapping("/inventory-by-category")
    public Map<String, Object> inventoryByCategory() {
        return dashboardService.inventoryByCategory();
    }

    @GetMapping("/rating-distribution")
    public Map<String, Object> ratingDistribution() {
        return dashboardService.ratingDistribution();
    }
}
