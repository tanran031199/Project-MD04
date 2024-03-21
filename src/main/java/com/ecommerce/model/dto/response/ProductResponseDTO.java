package com.ecommerce.model.dto.response;

import com.ecommerce.model.dto.validate.EmailRgxConstraint;
import com.ecommerce.model.entity.Category;
import com.ecommerce.model.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private String productId;

    @Min(value = 0, message = "Tồn kho không được < 0")
    private int stock;

    @NotNull(message = "Trường này là bắt buộc")
    private String productName;

    @NotNull(message = "Trường này là bắt buộc")
    private String description;

    @Min(value = 1, message = "Giá mua vào phải > 1")
    private double importPrice;

    private double exportPrice;

    private Category category;

    private List<ProductImage> images;
}
