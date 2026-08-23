package com.erp.platform.controller;

import com.erp.platform.model.InventoryItem;
import com.erp.platform.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", inventoryService.findAll());
        model.addAttribute("newItem", new InventoryItem());
        return "inventory";
    }

    @PostMapping
    public String create(@ModelAttribute("newItem") InventoryItem item) {
        inventoryService.save(item);
        return "redirect:/inventory";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        inventoryService.deleteById(id);
        return "redirect:/inventory";
    }
}
