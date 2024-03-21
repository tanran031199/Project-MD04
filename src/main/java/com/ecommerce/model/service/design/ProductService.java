package com.ecommerce.model.service.design;

import com.ecommerce.model.dto.response.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    List<ProductResponseDTO> findProductByCategory(Integer categoryId);
    ProductResponseDTO findOne(String productId);
    boolean create(ProductResponseDTO productResponseDTO, List<MultipartFile> multiFiles);
    boolean deleteProductImage(String productId, Integer imageId);

    boolean update(ProductResponseDTO productResponseDTO);

    boolean updateProductImage(String productId, List<MultipartFile> multiFiles);

    boolean delete(String productId);
}
