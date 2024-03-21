package com.ecommerce.model.service.design;

import com.ecommerce.model.dto.response.CategoryResponseDTO;
import com.ecommerce.model.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO findOne(int id);
    boolean create(CategoryResponseDTO categoryResponseDTO);
    boolean update(CategoryResponseDTO categoryResponseDTO);
    boolean delete(int id);
    public Map<Category, List<Category>> getCategoryHierarchy();
}
