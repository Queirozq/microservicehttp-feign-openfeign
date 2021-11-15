package com.Queirozq.invetory.service.api;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class InventoryServiceApi {
    private static Map<UUID,Product> productMap = new HashMap<>();

    static{
        UUID productId = UUID.fromString("5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2");
        productMap.put(productId, new Product(productId, "Phone", 5));
    }

    @PostMapping("/products")
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request){
        String productName = request.getName();
        int initialStock = request.getInitialStock();
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, productName, initialStock);
        productMap.put(productId, product);
        return CreateProductResponse.builder()
                .productId(productId)
                .name(productName)
                .stock(initialStock)
                .build();
    }
}
