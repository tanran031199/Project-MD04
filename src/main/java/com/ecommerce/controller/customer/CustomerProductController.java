package com.ecommerce.controller.customer;

import com.ecommerce.model.entity.Category;
import com.ecommerce.model.service.design.CategoryService;
import com.ecommerce.model.service.design.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class CustomerProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String toProductPage(@RequestParam(value = "cid", required = false) Integer categoryId, ModelMap modelMap) {
        Map<Category, List<Category>> categoryHierarchy = categoryService.getCategoryHierarchy();
        modelMap.addAttribute("categoryHierarchy", categoryHierarchy);

        if(categoryId != null) {
            modelMap.addAttribute("products", productService.findProductByCategory(categoryId));
        } else {
            modelMap.addAttribute("products", productService.findAll());
        }
        return "customer/product/product";
    }
}
