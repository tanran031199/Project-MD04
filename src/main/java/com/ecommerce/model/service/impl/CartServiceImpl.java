package com.ecommerce.model.service.impl;

import com.ecommerce.model.dao.design.CartDAO;
import com.ecommerce.model.dao.design.ProductDAO;
import com.ecommerce.model.dto.response.CartItemResponseDTO;
import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.entity.CartItem;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.service.design.CartService;
import com.ecommerce.model.service.design.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDAO productDAO;

    @Override
    public Cart getCartByUserId(Integer userId) {
        return cartDAO.getCartByUserId(userId);
    }

    @Override
    public boolean create(Cart cart) {
        return cartDAO.create(cart);
    }

    @Override
    public boolean addToCart(Integer cartId, ProductResponseDTO productResponseDTO, Integer quantity) {
        return cartDAO.addToCart(cartId, productResponseDTO, quantity);
    }

    @Override
    public boolean checkProductExists(Integer cartId, String productId) {
        return cartDAO.checkProductExists(cartId, productId);
    }

    @Override
    public List<CartItemResponseDTO> getListCartItems(Integer cartId) {
        List<CartItemResponseDTO> cartItemResponse = new ArrayList<>();
        List<CartItem> cartItemList = cartDAO.getListCartItems(cartId);
        ModelMapper modelMapper = new ModelMapper();

        for (CartItem c : cartItemList) {
            CartItemResponseDTO cartItemRsp = modelMapper.map(c, CartItemResponseDTO.class);
            cartItemRsp.setProduct(productService.findOne(c.getProductId()));
            cartItemResponse.add(cartItemRsp);
        }

        return cartItemResponse;
    }

    @Override
    public void delete(Integer cartItemId) {
        cartDAO.deleteCartItem(cartItemId);
    }

    @Override
    public void deleteAll(Integer cartId) {
        cartDAO.deleteAll(cartId);
    }

    @Override
    public boolean checkout(List<CartItemResponseDTO> cartItemList, Integer cartId) {
        boolean checkout = false;

        for (CartItemResponseDTO c : cartItemList) {
            Product product = productDAO.findOne(c.getProduct().getProductId());
            product.setStock(product.getStock() - c.getQuantity());
            checkout = productDAO.update(product);
        }

        return checkout && cartDAO.checkout(cartId);
    }
}
