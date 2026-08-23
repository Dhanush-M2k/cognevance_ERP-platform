package com.erp.platform.controller;

import com.erp.platform.model.FinanceRecord;
import com.erp.platform.service.FinanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("records", financeService.findAll());
        model.addAttribute("newRecord", new FinanceRecord());
        model.addAttribute("totalIncome", financeService.totalIncome());
        model.addAttribute("totalExpense", financeService.totalExpense());
        model.addAttribute("netProfit", financeService.netProfit());
        return "finance";
    }

    @PostMapping
    public String create(@ModelAttribute("newRecord") FinanceRecord record) {
        financeService.save(record);
        return "redirect:/finance";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        financeService.deleteById(id);
        return "redirect:/finance";
    }
}
