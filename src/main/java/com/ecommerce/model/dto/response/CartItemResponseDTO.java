package com.ecommerce.model.dto.response;

import com.ecommerce.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemResponseDTO {
    private int cartItemId, cartId, quantity;
    private ProductResponseDTO product;
    private double unitPrice, totalPrice;
}
