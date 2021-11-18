package com.Queirozq.onlinestore.service.external.inventory;
import com.Queirozq.onlinestore.service.external.BaseClient;
import com.Queirozq.onlinestore.service.external.error.HandleFeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;

@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface InventoryServiceClient extends BaseClient {

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @HandleFeignException(ProductCreationFailedExceptionHandler.class)
    CreateProductResponse createProduct(CreateProductRequest request);

    @PostMapping("/products/{productId}/buy?amount={amount}&boughtAt={boughtAt}")
    void buy(@PathVariable("productId") String productId,
             @RequestParam("amount") int amount,
             @RequestParam(value = "boughtAt") OffsetDateTime boughtAt);
}
