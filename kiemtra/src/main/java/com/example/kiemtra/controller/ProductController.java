package com.example.kiemtra.controller;

import com.example.kiemtra.model.Product;
import com.example.kiemtra.service.CategoryService;
import com.example.kiemtra.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/nhanviens")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/products-list";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("name") String name, Model model) {
        List<Product> products = productService.searchProductsByName(name);
        model.addAttribute("products", products);
        return "products/products-list"; // Sửa tên mẫu từ "product-list" thành "products-list"
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product()); // Sửa từ "products" thành "product"
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/add-products";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "products/add-products"; // Đảm bảo đúng đường dẫn
        }

        productService.addProduct(product);
        return "redirect:/nhanviens"; // Sửa từ "/products" thành "/nhanviens"
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/update-products"; // Đảm bảo đúng đường dẫn
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            product.setId(id);
            return "products/update-products"; // Đảm bảo đúng đường dẫn
        }

        productService.updateProduct(product);
        return "redirect:/nhanviens"; // Sửa từ "/products" thành "/nhanviens"
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/nhanviens"; // Sửa từ "/products" thành "/nhanviens"
    }
}
