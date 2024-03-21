package com.ecommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int categoryId, stock;
    private String productName, description, productId;
    private double importPrice, exportPrice;
}
