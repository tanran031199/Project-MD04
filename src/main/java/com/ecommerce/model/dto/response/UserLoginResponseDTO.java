package com.ecommerce.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO {
    private int userId, age;
    private String userName, email, phoneNumber, address, photoUrl;
    private byte role, status;
}
