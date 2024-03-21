package com.ecommerce.model.dao.impl;

import com.ecommerce.config.Constant.UserQuery;
import com.ecommerce.model.dao.design.UserDAO;
import com.ecommerce.model.entity.User;
import com.ecommerce.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {
    @Override
    public List<User> findAll() {
        Connection connection = ConnectionDB.getConnection();
        List<User> users;

        try {
            CallableStatement cstm = connection.prepareCall(UserQuery.FIND_ALL.getQuery());
            ResultSet rs = cstm.executeQuery();
            users = new ArrayList<>();

            while(rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPhotoUrl(rs.getString("photo_url"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getByte("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User findOne(Integer userId) {
        return null;
    }

    @Override
    public Boolean create(User user) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(UserQuery.REGISTER.getQuery());
            cstm.setString(1, user.getUserName());
            cstm.setString(2, user.getEmail());
            cstm.setString(3, user.getPassword());
            cstm.setInt(4, user.getRole());

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
    public Boolean update(User user) {
        return null;
    }

    @Override
    public Boolean delete(Integer integer) {
        return null;
    }


    @Override
    public boolean checkExistsEmail(String email) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(UserQuery.GET_EMAIL.getQuery());
            cstm.setString(1, email);

            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public User login(String email, String password) {
        Connection connection = ConnectionDB.getConnection();
        User user = null;

        try {
            CallableStatement cstm = connection.prepareCall(UserQuery.GET_BY_EMAIL.getQuery());
            cstm.setString(1, email);

            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPhotoUrl(rs.getString("photo_url"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getByte("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return user;
    }
}
