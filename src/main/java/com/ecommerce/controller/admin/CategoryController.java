package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.CategoryResponseDTO;
import com.ecommerce.model.service.design.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String toAdminCategory(@RequestParam(name = "id", required = false) Integer categoryId, ModelMap modelMap) {
        List<CategoryResponseDTO> allCategory = categoryService.findAll();

        List<CategoryResponseDTO> subCategory = allCategory.stream()
                .filter(e -> e.getParentId() != 0)
                .collect(Collectors.toList());

        List<CategoryResponseDTO> parentCategory = allCategory.stream()
                .filter(e -> e.getParentId() == 0)
                .collect(Collectors.toList());

        if (categoryId == null) {
            modelMap.addAttribute("category", new CategoryResponseDTO());
            modelMap.addAttribute("categories", subCategory);
            modelMap.addAttribute("parentCategory", parentCategory);
        } else {
            modelMap.addAttribute("category", categoryService.findOne(categoryId));
            modelMap.addAttribute("categories", subCategory);
            modelMap.addAttribute("parentCategory", parentCategory);
        }

        return "admin/category/category";
    }

    @PostMapping("")
    public String createCategory(@ModelAttribute CategoryResponseDTO category) {
        System.out.println(category.getCategoryId());
        if (category.getCategoryId() == 0) {
            categoryService.create(category);
        } else {
            categoryService.update(category);
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int categoryId) {
        System.out.println(categoryId);
        categoryService.delete(categoryId);
        return "redirect:/admin/category";
    }
}
