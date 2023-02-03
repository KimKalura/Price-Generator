package com.spring.priceGenerator.controller;

import com.spring.priceGenerator.dto.AddProductDTO;
import com.spring.priceGenerator.model.Product;
import com.spring.priceGenerator.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody AddProductDTO addProductDTO) {
        return productService.addProduct(addProductDTO);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
