package com.ecomerce_data_model.productservice.service;

import com.ecomerce_data_model.productservice.entity.Product;
import com.ecomerce_data_model.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public boolean updateProduct(Long productId, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            updatedProduct.setProductId(productId);
            productRepository.save(updatedProduct);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteProduct(Long productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }
}
