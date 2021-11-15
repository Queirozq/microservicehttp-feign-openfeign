package com.Queirozq.invetory.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private int stock;

}
