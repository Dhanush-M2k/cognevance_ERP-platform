package com.erp.platform.controller;

import com.erp.platform.model.Sale;
import com.erp.platform.service.SalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sales", salesService.findAll());
        model.addAttribute("newSale", new Sale());
        return "sales";
    }

    @PostMapping
    public String create(@ModelAttribute("newSale") Sale sale) {
        salesService.save(sale);
        return "redirect:/sales";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        salesService.deleteById(id);
        return "redirect:/sales";
    }
}
