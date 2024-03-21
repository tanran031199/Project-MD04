package com.ecommerce.model.dao.design;

import com.ecommerce.model.entity.User;

public interface UserDAO extends IGenericDAO<User, Integer> {
    boolean checkExistsEmail(String email);
    User login(String email, String password);
}
