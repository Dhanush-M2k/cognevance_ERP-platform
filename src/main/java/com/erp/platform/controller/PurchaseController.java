package com.erp.platform.controller;

import com.erp.platform.model.Purchase;
import com.erp.platform.service.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/purchases")
    public String purchasesPage(Model model) {
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        model.addAttribute("totalPurchaseCost", purchaseService.getTotalPurchaseCost());
        model.addAttribute("newPurchase", new Purchase());
        return "purchase";
    }

    @PostMapping("/purchases/add")
    public String addPurchase(@ModelAttribute Purchase newPurchase,
                               RedirectAttributes redirectAttributes) {
        purchaseService.createPurchase(newPurchase);
        redirectAttributes.addFlashAttribute("message", "Purchase order created.");
        return "redirect:/purchases";
    }

    @PostMapping("/purchases/{id}/delete")
    public String deletePurchase(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        purchaseService.deletePurchase(id);
        redirectAttributes.addFlashAttribute("message", "Purchase order removed.");
        return "redirect:/purchases";
    }
  
}
