package com.Queirozq.onlinestore.service.external.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductResponse {
    private UUID productId;
    private String name;
    private int initialStock;
}
