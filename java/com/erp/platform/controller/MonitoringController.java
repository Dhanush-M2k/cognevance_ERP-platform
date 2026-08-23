package com.erp.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonitoringController {

    @GetMapping("/monitoring")
    public String monitoringPage() {
        return "monitoring";
    }
}
