package com.Queirozq.onlinestore.service.external;

import org.springframework.web.bind.annotation.GetMapping;

public interface BaseClient {
    @GetMapping("/actuator/health")
    ActuatorHealthResponse health();
}
