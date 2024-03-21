package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.service.design.CategoryService;
import com.ecommerce.model.service.design.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String toProductTable(@RequestParam(name = "id", required = false) String id, ModelMap modelMap) {
        if(id != null) {
            modelMap.addAttribute("product", productService.findOne(id));
        }

        modelMap.addAttribute("products", productService.findAll());
        return "admin/product/product";
    }

    @GetMapping("create")
    public String showCreateForm(ModelMap modelMap) {
        modelMap.addAttribute("product", new ProductResponseDTO());
        modelMap.addAttribute("categories", categoryService.findAll());
        return "admin/product/add-product";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute ProductResponseDTO productResponseDTO,
                         BindingResult result,
                         @RequestParam(name = "multiFiles", required = false) List<MultipartFile> multiFiles,
                         ModelMap modelMap) {
        if (productResponseDTO.getProductId().isEmpty()) {
            productService.create(productResponseDTO, multiFiles);
        } else {
            if (!Objects.equals(multiFiles.get(0).getOriginalFilename(), "")) {
                productService.updateProductImage(productResponseDTO.getProductId(), multiFiles);
            }

            productService.update(productResponseDTO);
        }

        return "redirect:/admin/product";
    }

    @GetMapping("update")
    public String toUpdateForm(@RequestParam("id") String id,
                               @RequestParam(name = "imageId", required = false) Integer imageId,
                               ModelMap modelMap) {

        if (imageId == null) {
            modelMap.addAttribute("product", productService.findOne(id));
            modelMap.addAttribute("categories", categoryService.findAll());
        } else {
            productService.deleteProductImage(id, imageId);
            modelMap.addAttribute("product", productService.findOne(id));
            modelMap.addAttribute("categories", categoryService.findAll());
        }

        return "admin/product/add-product";
    }

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable("id") String productId) {
        productService.delete(productId);
        return "redirect:/admin/product";
    }
}
