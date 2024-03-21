package com.ecommerce.model.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Category {
    private int categoryId, parentId;
    private String categoryName, description;
    private boolean status;
}
