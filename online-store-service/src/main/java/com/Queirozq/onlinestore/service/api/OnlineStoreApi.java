package com.Queirozq.onlinestore.service.api;

import com.Queirozq.onlinestore.service.external.inventory.CreateProductRequest;
import com.Queirozq.onlinestore.service.external.inventory.CreateProductResponse;
import com.Queirozq.onlinestore.service.external.inventory.InventoryServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OnlineStoreApi {
    private final InventoryServiceClient inventoryServiceClient;


    @PostMapping("/online-store/products")
    public CreateOnlineStoreProductResponse createProduct(@RequestBody CreateOnlineStoreProductRequest request){
        CreateProductResponse response = inventoryServiceClient.createProduct(new CreateProductRequest(request.getName(), request.getInitialStock()));
        return CreateOnlineStoreProductResponse.builder()
                .productId(response.getProductId())
                .name(response.getName())
                .initialStock(request.getInitialStock())
                .build();
    }
}
