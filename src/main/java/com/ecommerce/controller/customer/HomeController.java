package com.ecommerce.controller.customer;

import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.service.design.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"", "/home"})
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String home(ModelMap modelMap) {
        List<ProductResponseDTO> allProducts = productService.findAll();
        List<ProductResponseDTO> responseNewProducts = allProducts.stream().
                limit(12).collect(Collectors.toList());

        modelMap.addAttribute("newProducts", responseNewProducts);
        return "customer/index";
    }
}
