package com.erp.platform.controller;

import com.erp.platform.model.Feedback;
import com.erp.platform.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles the customer feedback / purchase review module.
 * Any authenticated user (customer, employee, manager, admin) can submit
 * feedback; staff roles can additionally see and manage all submissions.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("feedbackList", feedbackService.findRecent());
        model.addAttribute("newFeedback", new Feedback());
        model.addAttribute("averageRating", Math.round(feedbackService.averageRating() * 10.0) / 10.0);
        return "feedback";
    }

    @PostMapping
    public String create(@ModelAttribute("newFeedback") Feedback feedback) {
        feedbackService.save(feedback);
        return "redirect:/feedback";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        feedbackService.deleteById(id);
        return "redirect:/feedback";
    }
}
