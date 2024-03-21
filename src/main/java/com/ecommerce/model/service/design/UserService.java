package com.ecommerce.model.service.design;

import com.ecommerce.model.dto.request.UserRegisterDTO;
import com.ecommerce.model.dto.response.UserLoginResponseDTO;

import java.util.List;

public interface UserService {
    boolean register(UserRegisterDTO userDTO);
    boolean checkExistsEmail(String email);
    UserLoginResponseDTO login(String email, String password);

    List<UserLoginResponseDTO> findAll();
}
