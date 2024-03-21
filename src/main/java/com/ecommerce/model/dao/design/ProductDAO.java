package com.ecommerce.model.dao.design;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.ProductImage;

import java.util.List;

public interface ProductDAO extends IGenericDAO<Product, String> {
    List<ProductImage> getImageByProductId(String productId);

    List<Product> findProductByCategory(Integer categoryId);

    boolean createImg(List<ProductImage> productImages);

    boolean deleteProductImage(String productId, Integer imageId);

    boolean updateProductImage(String productId, List<ProductImage> productImages);
}
