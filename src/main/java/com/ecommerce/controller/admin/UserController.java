package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.UserLoginResponseDTO;
import com.ecommerce.model.service.design.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAll());
        return "admin/user/users";
    }
}
