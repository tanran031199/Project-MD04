package com.ecommerce.model.service.impl;

import com.ecommerce.model.dao.design.CategoryDAO;
import com.ecommerce.model.dto.response.CategoryResponseDTO;
import com.ecommerce.model.entity.Category;
import com.ecommerce.model.service.design.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryDAO.findAll();
        List<CategoryResponseDTO> responseList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (int i = 0; i < categories.size(); i++) {
            CategoryResponseDTO categoryResponseDTO = modelMapper.map(categories.get(i), CategoryResponseDTO.class);

            if (categoryResponseDTO.getParentId() != 0) {
                categoryResponseDTO.setParentName(categoryDAO.findOne(categoryResponseDTO.getParentId()).getCategoryName());
            }

            responseList.add(categoryResponseDTO);
        }

        return responseList;
    }

    @Override
    public CategoryResponseDTO findOne(int id) {
        ModelMapper modelMapper = new ModelMapper();
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(categoryDAO.findOne(id), CategoryResponseDTO.class);

        if(categoryResponseDTO.getParentId() != 0) {
        categoryResponseDTO.setCategoryName(categoryDAO.findOne(categoryResponseDTO.getParentId()).getCategoryName());
        }
        return categoryResponseDTO;
    }

    @Override
    public boolean create(CategoryResponseDTO categoryResponseDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(categoryResponseDTO, Category.class);
        return categoryDAO.create(category);
    }

    @Override
    public boolean update(CategoryResponseDTO categoryResponseDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(categoryResponseDTO, Category.class);
        return categoryDAO.update(category);
    }

    @Override
    public boolean delete(int id) {
        return categoryDAO.delete(id);
    }

    @Override
    public Map<Category, List<Category>> getCategoryHierarchy() {
        return categoryDAO.getCategoryHierarchy();
    }
}
