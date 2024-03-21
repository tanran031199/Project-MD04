package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.UserLoginResponseDTO;
import com.ecommerce.model.service.design.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile() {
        return "admin/account/profile";
    }

}
