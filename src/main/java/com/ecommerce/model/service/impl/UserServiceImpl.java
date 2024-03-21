package com.ecommerce.model.service.impl;

import com.ecommerce.model.dao.design.UserDAO;
import com.ecommerce.model.dto.request.UserRegisterDTO;
import com.ecommerce.model.dto.response.UserLoginResponseDTO;
import com.ecommerce.model.entity.User;
import com.ecommerce.model.service.design.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean register(UserRegisterDTO userDTO) {
        String hasPass = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12));
        userDTO.setPassword(hasPass);
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        return userDAO.create(user);
    }

    @Override
    public boolean checkExistsEmail(String email) {
        return userDAO.checkExistsEmail(email);
    }

    @Override
    public UserLoginResponseDTO login(String email, String password) {
        User user = userDAO.login(email, password);
        ModelMapper modelMapper = new ModelMapper();

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return modelMapper.map(user, UserLoginResponseDTO.class);
        }

        return null;
    }

    @Override
    public List<UserLoginResponseDTO> findAll() {
        List<User> users = userDAO.findAll();
        List<UserLoginResponseDTO> usersDto = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (User u : users) {
            UserLoginResponseDTO userLoginResponseDTO = modelMapper.map(u, UserLoginResponseDTO.class);
            usersDto.add(userLoginResponseDTO);
        }

        return usersDto;
    }
}
