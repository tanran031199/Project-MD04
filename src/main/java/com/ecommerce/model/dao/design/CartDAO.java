package com.ecommerce.model.dao.design;

import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.entity.CartItem;

import java.util.List;

public interface CartDAO extends IGenericDAO<Cart, Integer> {
    Cart getCartByUserId(Integer userId);

    boolean addToCart(Integer cartId, ProductResponseDTO productResponseDTO, Integer quantity);
    boolean checkProductExists(Integer cartId, String productId);

    List<CartItem> getListCartItems(Integer cartId);

    void deleteCartItem(Integer cartItemId);

    void deleteAll(Integer cartId);

    boolean checkout(Integer cartId);
}
