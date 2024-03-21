package com.ecommerce.model.service.impl;

import com.ecommerce.model.dao.design.CategoryDAO;
import com.ecommerce.model.dao.design.ProductDAO;
import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.ProductImage;
import com.ecommerce.model.service.design.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private String uploadPath = "D:\\Rikkei academy\\Moudle04\\project\\my-project\\src\\main\\webapp\\WEB-INF\\upload\\";

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productDAO.findAll();
        List<ProductResponseDTO> responseProducts = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (int i = 0; i < products.size(); i++) {
            ProductResponseDTO productResponseDTO = modelMapper.map(products.get(i), ProductResponseDTO.class);

            if (products.get(i).getCategoryId() != 0) {
                productResponseDTO.setCategory(categoryDAO.findOne(products.get(i).getCategoryId()));
                productResponseDTO.setImages(productDAO.getImageByProductId(products.get(i).getProductId()));
            }

            responseProducts.add(productResponseDTO);
        }

        return responseProducts;
    }

    @Override
    public ProductResponseDTO findOne(String productId) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = productDAO.findOne(productId);

        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
        productResponseDTO.setCategory(categoryDAO.findOne(product.getCategoryId()));
        productResponseDTO.setImages(productDAO.getImageByProductId(product.getProductId()));

        return productResponseDTO;
    }

    @Override
    public boolean create(ProductResponseDTO productResponseDTO, List<MultipartFile> multiFiles) {
        Product product = new Product();
        product.setProductName(productResponseDTO.getProductName());
        product.setDescription(productResponseDTO.getDescription());
        product.setStock(productResponseDTO.getStock());
        product.setImportPrice(productResponseDTO.getImportPrice());
        product.setCategoryId(productResponseDTO.getCategory().getCategoryId());
        product.setStock(productResponseDTO.getStock());

        List<ProductImage> productImages = new ArrayList<>();

        String uuid = String.valueOf(UUID.randomUUID());
        product.setProductId(uuid);



        if (!Objects.equals(multiFiles.get(0).getOriginalFilename(), "")) {
            for (int i = 0; i < multiFiles.size(); i++) {
                String fileName = multiFiles.get(i).getOriginalFilename();
                ProductImage productImage = new ProductImage();
                productImage.setProductId(uuid);
                productImage.setFileName(fileName);
                productImages.add(productImage);

                try {
                    FileCopyUtils.copy(multiFiles.get(i).getBytes(),new File(uploadPath+fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return productDAO.create(product) && productDAO.createImg(productImages);
    }

    @Override
    public boolean deleteProductImage(String productId, Integer imageId) {
        return productDAO.deleteProductImage(productId, imageId);
    }

    @Override
    public boolean update(ProductResponseDTO productResponseDTO) {
        Product product = new Product();
        product.setProductId(productResponseDTO.getProductId());
        product.setProductName(productResponseDTO.getProductName());
        product.setDescription(productResponseDTO.getDescription());
        product.setStock(productResponseDTO.getStock());
        product.setImportPrice(productResponseDTO.getImportPrice());
        product.setCategoryId(productResponseDTO.getCategory().getCategoryId());
        product.setStock(productResponseDTO.getStock());

        return productDAO.update(product);
    }

    @Override
    public boolean updateProductImage(String productId, List<MultipartFile> multiFiles) {
        List<ProductImage> productImages = new ArrayList<>();

        if (!Objects.equals(multiFiles.get(0).getOriginalFilename(), "")) {
            for (int i = 0; i < multiFiles.size(); i++) {
                String fileName = multiFiles.get(i).getOriginalFilename();
                ProductImage productImage = new ProductImage();
                productImage.setProductId(productId);
                productImage.setFileName(fileName);
                productImages.add(productImage);

                try {
                    FileCopyUtils.copy(multiFiles.get(i).getBytes(),new File(uploadPath+fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return productDAO.updateProductImage(productId, productImages);
    }

    @Override
    public boolean delete(String productId) {
        return productDAO.delete(productId);
    }

    @Override
    public List<ProductResponseDTO> findProductByCategory(Integer categoryId) {
        List<Product> products = productDAO.findProductByCategory(categoryId);
        List<ProductResponseDTO> responseProducts = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (int i = 0; i < products.size(); i++) {
            ProductResponseDTO productResponseDTO = modelMapper.map(products.get(i), ProductResponseDTO.class);

            if (products.get(i).getCategoryId() != 0) {
                productResponseDTO.setCategory(categoryDAO.findOne(products.get(i).getCategoryId()));
                productResponseDTO.setImages(productDAO.getImageByProductId(products.get(i).getProductId()));
            }

            responseProducts.add(productResponseDTO);
        }

        return responseProducts;
    }
}
