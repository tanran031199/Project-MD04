package com.ecommerce.model.dao.impl;

import com.ecommerce.config.Constant.ProductQuery;
import com.ecommerce.model.dao.design.ProductDAO;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.ProductImage;
import com.ecommerce.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDAO {
    @Override
    public List<Product> findAll() {
        Connection connection = ConnectionDB.getConnection();
        List<Product> products;

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.FIND_ALL.getQuery());
            products = new ArrayList<>();
            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setExportPrice(rs.getDouble("export_price"));
                product.setStock(rs.getInt("stock"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return products;
    }

    @Override
    public Product findOne(String productId) {
        Connection connection = ConnectionDB.getConnection();
        Product product = null;

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.FIND_ONE.getQuery());
            cstm.setString(1, productId);
            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setExportPrice(rs.getDouble("export_price"));
                product.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return product;
    }

    @Override
    public Boolean create(Product product) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.CREATE.getQuery());

            cstm.setString(1, product.getProductId());
            cstm.setInt(2, product.getCategoryId());
            cstm.setString(3, product.getProductName());
            cstm.setString(4, product.getDescription());
            cstm.setDouble(5, product.getImportPrice());
            cstm.setInt(6, product.getStock());

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
    public Boolean update(Product product) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.UPDATE.getQuery());

            cstm.setString(1, product.getProductId());
            cstm.setInt(2, product.getCategoryId());
            cstm.setString(3, product.getProductName());
            cstm.setString(4, product.getDescription());
            cstm.setDouble(5, product.getImportPrice());
            cstm.setInt(6, product.getStock());

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
    public Boolean delete(String productId) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.DELETE.getQuery());
            cstm.setString(1, productId);

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
    public List<ProductImage> getImageByProductId(String productId) {
        Connection connection = ConnectionDB.getConnection();
        List<ProductImage> productImages;

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.GET_IMAGE_BY_PRODUCT_ID.getQuery());
            cstm.setString(1, productId);
            productImages = new ArrayList<>();
            ResultSet rs = cstm.executeQuery();
            ;

            while (rs.next()) {
                ProductImage productImage = new ProductImage();
                productImage.setImageId(rs.getInt("image_id"));
                productImage.setProductId(rs.getString("product_id"));
                productImage.setFileName(rs.getString("file_name"));
                productImages.add(productImage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return productImages;
    }

    @Override
    public boolean createImg(List<ProductImage> productImages) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.CREATE_IMG.getQuery());
            int check = 0;

            for (int i = 0; i < productImages.size(); i++) {
                ProductImage productImage = productImages.get(i);
                cstm.setString(1, productImage.getProductId());
                cstm.setString(2, productImage.getFileName());
                check += cstm.executeUpdate();
            }

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
    public boolean updateProductImage(String productId, List<ProductImage> productImages) {
        Connection connection = ConnectionDB.getConnection();
        int checkDelete = 0;
        int checkUpdate = 0;

        try {
            CallableStatement cstmUpdate = connection.prepareCall(ProductQuery.CREATE_IMG.getQuery());
            CallableStatement cstmDelete = connection.prepareCall(ProductQuery.DELETE_ALL_IMG_BY_PRODUCT_ID.getQuery());

            cstmDelete.setString(1, productId);
            checkDelete = cstmDelete.executeUpdate();

            for (int i = 0; i < productImages.size(); i++) {
                cstmUpdate.setString(1, productId);
                cstmUpdate.setString(2, productImages.get(i).getFileName());
                checkUpdate += cstmUpdate.executeUpdate();
            }

            if (checkDelete > 0 && checkUpdate > 0) {
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
    public boolean deleteProductImage(String productId, Integer imageId) {
        Connection connection = ConnectionDB.getConnection();

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.DELETE_IMG.getQuery());
            cstm.setString(1, productId);
            cstm.setInt(2, imageId);

            int check = cstm.executeUpdate();

            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public List<Product> findProductByCategory(Integer categoryId) {
        Connection connection = ConnectionDB.getConnection();
        List<Product> products;

        try {
            CallableStatement cstm = connection.prepareCall(ProductQuery.FIND_BY_CATEGORY_ID.getQuery());
            cstm.setInt(1, categoryId);
            products = new ArrayList<>();
            ResultSet rs = cstm.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setImportPrice(rs.getDouble("import_price"));
                product.setExportPrice(rs.getDouble("export_price"));
                product.setStock(rs.getInt("stock"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return products;
    }
}
