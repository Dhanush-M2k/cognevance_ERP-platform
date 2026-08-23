package com.erp.platform.controller;

import com.erp.platform.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "logout", required = false) String logout,
                             Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("infoMessage", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("fullName")) {
            model.addAttribute("fullName", "");
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String fullName,
                                @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                Model model) {

        if (username == null || username.isBlank() || email == null || email.isBlank()
                || password == null || password.isBlank() || fullName == null || fullName.isBlank()) {
            model.addAttribute("errorMessage", "All fields are required.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "register";
        }

        if (password.length() < 6) {
            model.addAttribute("errorMessage", "Password must be at least 6 characters long.");
            return "register";
        }

        if (userService.usernameExists(username)) {
            model.addAttribute("errorMessage", "Username is already taken.");
            return "register";
        }

        if (userService.emailExists(email)) {
            model.addAttribute("errorMessage", "An account with this email already exists.");
            return "register";
        }

        userService.registerCustomer(username, email, password, fullName);
        model.addAttribute("successMessage", "Registration successful! You can now log in.");
        return "login";
    }
}
