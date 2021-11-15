package com.Queirozq.onlinestore.service.external.inventory;

import feign.Headers;
import feign.RequestLine;

public interface InventoryServiceClient {
    @RequestLine("POST /products")
    @Headers("Content-Type: application/json")
    CreateProductResponse createProduct(CreateProductRequest request);
}
