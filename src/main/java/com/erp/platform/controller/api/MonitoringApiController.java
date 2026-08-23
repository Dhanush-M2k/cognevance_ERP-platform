package com.erp.platform.controller.api;

import com.erp.platform.service.MonitoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringApiController {

    private final MonitoringService monitoringService;

    public MonitoringApiController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/live")
    public Map<String, Object> live() {
        return monitoringService.currentSnapshot();
    }
}
