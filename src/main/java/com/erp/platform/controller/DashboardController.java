package com.erp.platform.controller;

import com.erp.platform.security.CustomUserDetails;
import com.erp.platform.service.DashboardService;
import com.erp.platform.service.FeedbackService;
import com.erp.platform.service.InventoryService;
import com.erp.platform.service.SalesService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final SalesService salesService;
    private final InventoryService inventoryService;
    private final FeedbackService feedbackService;

    public DashboardController(DashboardService dashboardService,
                                SalesService salesService,
                                InventoryService inventoryService,
                                FeedbackService feedbackService) {
        this.dashboardService = dashboardService;
        this.salesService = salesService;
        this.inventoryService = inventoryService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        model.addAttribute("summary", dashboardService.summary());
        model.addAttribute("recentSales", salesService.findRecent());
        model.addAttribute("lowStock", inventoryService.findLowStock());
        model.addAttribute("recentFeedback", feedbackService.findRecent());
        model.addAttribute("currentUser", principal);
        return "dashboard";
    }
}
