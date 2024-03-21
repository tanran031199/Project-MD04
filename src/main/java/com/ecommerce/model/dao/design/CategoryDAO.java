package com.ecommerce.model.dao.design;

import com.ecommerce.model.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDAO extends IGenericDAO<Category, Integer> {
    Map<Category, List<Category>> getCategoryHierarchy();
}
