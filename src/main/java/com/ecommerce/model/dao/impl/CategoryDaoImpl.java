package com.ecommerce.model.dao.impl;

import com.ecommerce.config.Constant.CategoryQuery;
import com.ecommerce.model.dao.design.CategoryDAO;
import com.ecommerce.model.entity.Category;
import com.ecommerce.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryDaoImpl implements CategoryDAO {
    @Override
    public List<Category> findAll() {
        Connection connection = ConnectionDB.getConnection();
        List<Category> categories = new ArrayList<>();

        try {
            CallableStatement cstm = connection.prepareCall(CategoryQuery.FIND_ALL.getQuery());
            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setParentId(rs.getInt("parent_id"));
                category.setStatus(rs.getBoolean("status"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category findOne(Integer categoryId) {
        Connection connection = ConnectionDB.getConnection();
        Category category = null;

        try {
            CallableStatement cstm = connection.prepareCall(CategoryQuery.FIND_ONE.getQuery());
            cstm.setInt(1, categoryId);

            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setParentId(rs.getInt("parent_id"));
                category.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return category;
    }

    @Override
    public Boolean create(Category category) {
        Connection connection = ConnectionDB.getConnection();
        CallableStatement cstm;

        try {
            if (category.getParentId() != 0) {
                cstm = connection.prepareCall(CategoryQuery.CREATE.getQuery());
                cstm.setInt(4, category.getParentId());
            } else {
                cstm = connection.prepareCall(CategoryQuery.CREATE_NO_PARENT.getQuery());
            }

            cstm.setString(1, category.getCategoryName());
            cstm.setString(2, category.getDescription());
            cstm.setBoolean(3, category.isStatus());

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
    public Boolean update(Category category) {
        Connection connection = ConnectionDB.getConnection();
        CallableStatement cstm;

        try {
            if (category.getParentId() != 0) {
                cstm = connection.prepareCall(CategoryQuery.UPDATE.getQuery());
                cstm.setInt(5, category.getParentId());
            } else {
                cstm = connection.prepareCall(CategoryQuery.UPDATE_NO_PARENT.getQuery());
            }

            cstm.setInt(1, category.getCategoryId());
            cstm.setString(2, category.getCategoryName());
            cstm.setString(3, category.getDescription());
            cstm.setBoolean(4, category.isStatus());

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
    public Boolean delete(Integer categoryId) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(CategoryQuery.DELETE.getQuery());
            cstm.setInt(1, categoryId);

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
    public Map<Category, List<Category>> getCategoryHierarchy() {
        Connection connection = ConnectionDB.getConnection();
        Map<Category, List<Category>> categoryHierarchy = null;

        try {
            CallableStatement cstm = connection.prepareCall(CategoryQuery.GET_CATEGORY_HIERARCHY.getQuery());
            ResultSet rs = cstm.executeQuery();
            categoryHierarchy = new HashMap<>();

            while (rs.next()) {
                Category parentCategory = new Category();
                Category subCategory = new Category();

                parentCategory.setCategoryId(rs.getInt("parent_cate_id"));
                parentCategory.setCategoryName(rs.getString("parent_cate_name"));
                parentCategory.setDescription(rs.getString("parent_cate_des"));
                subCategory.setCategoryId(rs.getInt("sub_cate_id"));
                subCategory.setCategoryName(rs.getString("sub_cate_name"));
                subCategory.setDescription(rs.getString("sub_cate_des"));
                subCategory.setParentId(rs.getInt("sub_cate_parent_id"));

                categoryHierarchy.putIfAbsent(parentCategory, new ArrayList<>());

                categoryHierarchy.get(parentCategory).add(subCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoryHierarchy;
    }
}
