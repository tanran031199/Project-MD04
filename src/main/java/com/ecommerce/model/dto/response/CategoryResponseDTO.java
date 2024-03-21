package com.ecommerce.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryResponseDTO {
    private int categoryId, parentId;
    private String categoryName, description, parentName;
    private boolean status;
}
