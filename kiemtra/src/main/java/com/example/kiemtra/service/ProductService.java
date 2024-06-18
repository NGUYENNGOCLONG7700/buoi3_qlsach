package com.example.kiemtra.service;

import com.example.kiemtra.model.Product;
import com.example.kiemtra.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Add a new product to the database
    public Product addProduct(@Valid Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setLuong(product.getLuong());
        existingProduct.setNoisinh(product.getNoisinh());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPhai(product.getPhai());
        return productRepository.save(existingProduct);
    }

    // Save an image to the server
    public String saveImage(MultipartFile image) throws IOException {
        String folder = "src/main/resources/static/images/";
        Path path = Paths.get(folder);

        // Ensure the directory exists
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Save the image file
        byte[] bytes = image.getBytes();
        Path filePath = path.resolve(image.getOriginalFilename());
        Files.write(filePath, bytes);
        return "/images/" + image.getOriginalFilename();
    }

    // Delete a product by its id
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    // Search products by name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
