package com.ecommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int cartItemId, cartId, quantity;
    private String productId;
    private double unitPrice, totalPrice;
}
