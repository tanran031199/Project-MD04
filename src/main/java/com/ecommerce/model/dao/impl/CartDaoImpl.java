package com.ecommerce.model.dao.impl;

import com.ecommerce.config.Constant.CartQuery;
import com.ecommerce.model.dao.design.CartDAO;
import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.entity.CartItem;
import com.ecommerce.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDAO {
    @Override
    public Cart getCartByUserId(Integer userId) {
        Connection connection = ConnectionDB.getConnection();
        Cart cart = null;

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.GET_CART_BY_USER_ID.getQuery());
            cstm.setInt(1, userId);

            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return cart;
    }

    @Override
    public boolean addToCart(Integer cartId, ProductResponseDTO productResponseDTO, Integer quantity) {
        Connection connection = ConnectionDB.getConnection();
        String productId = productResponseDTO.getProductId();
        double exportPrice = productResponseDTO.getExportPrice();
        CallableStatement cstm;

        try {
            if (checkProductExists(cartId, productId)) {
                cstm = connection.prepareCall(CartQuery.UPDATE_CART_ITEM.getQuery());
            } else {
                cstm = connection.prepareCall(CartQuery.ADD_TO_CART.getQuery());
            }

            cstm.setString(1, productId);
            cstm.setInt(2, cartId);
            cstm.setInt(3, quantity);
            cstm.setDouble(4, exportPrice);

            int check = cstm.executeUpdate();

            if(check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean checkProductExists(Integer cartId, String productId) {
        Connection connection = ConnectionDB.getConnection();
        boolean isExists ;

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.CHECK_PRODUCT_EXISTS.getQuery());
            cstm.setInt(1, cartId);
            cstm.setString(2, productId);
            cstm.registerOutParameter(3, Types.INTEGER);

            cstm.execute();

            isExists = cstm.getInt(3) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return isExists;
    }

    @Override
    public List<CartItem> getListCartItems(Integer cartId) {
        Connection connection = ConnectionDB.getConnection();
        List<CartItem> cartItemList;

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.GET_LIST_CART_ITEM.getQuery());
            cstm.setInt(1, cartId);
            cartItemList = new ArrayList<>();

            ResultSet rs = cstm.executeQuery();

            while(rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("cart_item_id"));
                cartItem.setProductId(rs.getString("product_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setUnitPrice(rs.getDouble("price_per_unit"));
                cartItem.setTotalPrice(rs.getDouble("total_price"));
                cartItemList.add(cartItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return cartItemList;
    }

    @Override
    public void deleteCartItem(Integer cartItemId) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.DELETE_CART_ITEM.getQuery());
            cstm.setInt(1, cartItemId);
            cstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public void deleteAll(Integer cartId) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.DELETE_ALL_CART_ITEM.getQuery());
            cstm.setInt(1, cartId);
            cstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public boolean checkout(Integer cartId) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.CHECK_OUT.getQuery());
            cstm.setInt(1, cartId);

            int check = cstm.executeUpdate();

            if(check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return false;
    }

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findOne(Integer integer) {
        return null;
    }

    @Override
    public Boolean create(Cart cart) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(CartQuery.CREATE.getQuery());
            cstm.setInt(1, cart.getUserId());

            int check = cstm.executeUpdate();

            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return false;
    }

    @Override
    public Boolean update(Cart cart) {
        return null;
    }

    @Override
    public Boolean delete(Integer integer) {
        return null;
    }
}
