package com.erp.platform.service;

import com.erp.platform.model.Sale;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class DashboardService {

    private final EmployeeService employeeService;
    private final InventoryService inventoryService;
    private final SalesService salesService;
    private final FinanceService financeService;
    private final FeedbackService feedbackService;
    private final UserService userService;

    public DashboardService(EmployeeService employeeService,
                             InventoryService inventoryService,
                             SalesService salesService,
                             FinanceService financeService,
                             FeedbackService feedbackService,
                             UserService userService) {
        this.employeeService = employeeService;
        this.inventoryService = inventoryService;
        this.salesService = salesService;
        this.financeService = financeService;
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    public Map<String, Object> summary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalEmployees", employeeService.count());
        summary.put("activeEmployees", employeeService.countActive());
        summary.put("totalInventoryItems", inventoryService.count());
        summary.put("lowStockItems", inventoryService.findLowStock().size());
        summary.put("totalSales", salesService.count());
        summary.put("totalRevenue", salesService.totalRevenue());
        summary.put("totalIncome", financeService.totalIncome());
        summary.put("totalExpense", financeService.totalExpense());
        summary.put("netProfit", financeService.netProfit());
        summary.put("totalFeedback", feedbackService.count());
        summary.put("averageRating", Math.round(feedbackService.averageRating() * 10.0) / 10.0);
        summary.put("totalUsers", userService.countUsers());
        return summary;
    }

    public Map<String, Object> revenueSeries() {
        List<Sale> sales = salesService.findAll();
        Map<String, BigDecimal> byMonth = new LinkedHashMap<>();

        java.time.YearMonth current = java.time.YearMonth.now();
        for (int i = 5; i >= 0; i--) {
            java.time.YearMonth ym = current.minusMonths(i);
            String label = ym.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + ym.getYear();
            byMonth.put(label, BigDecimal.ZERO);
        }

        for (Sale sale : sales) {
            if (sale.getSaleDate() == null || sale.getTotalAmount() == null) continue;
            java.time.YearMonth ym = java.time.YearMonth.from(sale.getSaleDate());
            String label = ym.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + ym.getYear();
            if (byMonth.containsKey(label)) {
                byMonth.merge(label, sale.getTotalAmount(), BigDecimal::add);
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", byMonth.keySet());
        result.put("values", byMonth.values());
        return result;
    }

    public Map<String, Object> inventoryByCategory() {
        Map<String, Integer> byCategory = new LinkedHashMap<>();
        inventoryService.findAll().forEach(item -> {
            String category = item.getCategory() != null ? item.getCategory() : "Uncategorized";
            byCategory.merge(category, item.getQuantity() != null ? item.getQuantity() : 0, Integer::sum);
        });
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", byCategory.keySet());
        result.put("values", byCategory.values());
        return result;
    }

    public Map<String, Object> ratingDistribution() {
        int[] counts = new int[5];
        feedbackService.findAll().forEach(f -> {
            if (f.getRating() != null && f.getRating() >= 1 && f.getRating() <= 5) {
                counts[f.getRating() - 1]++;
            }
        });
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", List.of("1 Star", "2 Star", "3 Star", "4 Star", "5 Star"));
        result.put("values", List.of(counts[0], counts[1], counts[2], counts[3], counts[4]));
        return result;
    }
}
