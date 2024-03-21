package com.ecommerce.model.service.design;

import com.ecommerce.model.dto.response.CartItemResponseDTO;
import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.entity.Cart;

import java.util.List;

public interface CartService {
    public Cart getCartByUserId(Integer userId);

    boolean create(Cart cart);

    boolean addToCart(Integer cartId, ProductResponseDTO productResponseDTO, Integer quantity);
    boolean checkProductExists(Integer cartId, String productId);

    List<CartItemResponseDTO> getListCartItems(Integer cartId);

    void delete(Integer cartItemId);

    void deleteAll(Integer cartId);

    boolean checkout(List<CartItemResponseDTO> cartItemList, Integer cartId);
}
