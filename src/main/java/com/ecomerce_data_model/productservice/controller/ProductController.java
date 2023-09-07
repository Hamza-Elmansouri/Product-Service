package com.ecomerce_data_model.productservice.controller;


import com.ecomerce_data_model.productservice.entity.Product;
import com.ecomerce_data_model.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("inside createProduct in ProductController");
        Product createdOrder = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<Product> getProduct(@PathVariable("product_id") Long productId) {
        log.info("inside getProduct in ProductController");
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<String> updateProduct(@PathVariable("product_id") Long productId, @RequestBody Product product) {
        log.info("inside updateProduct in ProductController");
        boolean updated = productService.updateProduct(productId, product);
        if (updated) {
            return ResponseEntity.ok("Product updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product_id") Long productId) {
        log.info("inside deleteProduct in ProductController");
        boolean deleted = productService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
