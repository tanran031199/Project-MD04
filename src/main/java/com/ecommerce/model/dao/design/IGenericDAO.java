package com.ecommerce.model.dao.design;

import java.util.List;

public interface IGenericDAO<T, ID> {
    List<T> findAll();
    T findOne(ID id);
    Boolean create(T t);
    Boolean update(T t);
    Boolean delete(ID id);
}
