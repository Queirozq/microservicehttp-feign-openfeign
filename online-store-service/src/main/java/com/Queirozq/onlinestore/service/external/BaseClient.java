package com.Queirozq.onlinestore.service.external;

import feign.RequestLine;

public interface BaseClient {

    @RequestLine("GET /actuator/health")
    ActuatorHealthResponse health();
}
