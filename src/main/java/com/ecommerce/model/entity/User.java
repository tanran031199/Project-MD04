package com.ecommerce.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId, age;
    private String userName, email, password, phoneNumber, address, photoUrl;
    private byte role, status;
}
