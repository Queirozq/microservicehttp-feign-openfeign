package com.Queirozq.onlinestore.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthResponse {
    private String name;
    private String status;
}
