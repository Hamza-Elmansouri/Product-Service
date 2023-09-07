package com.ecomerce_data_model.productservice;

import com.ecomerce_data_model.productservice.controller.ProductController;
import com.ecomerce_data_model.productservice.entity.Product;
import com.ecomerce_data_model.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        Product createdProduct = new Product();
        when(productService.createProduct(product)).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
    }

    @Test
    void testGetProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetNotFoundProduct() {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProduct(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId(1L);

        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(true);

        ResponseEntity<String> response = productController.updateProduct(1L, product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated successfully", response.getBody());

        verify(productService).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        Product product = new Product();
        product.setProductId(1L);

        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(false);

        ResponseEntity<String> response = productController.updateProduct(1L, product);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(productService).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        when(productService.deleteProduct(1L)).thenReturn(true);

        ResponseEntity<String> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productService.deleteProduct(1L)).thenReturn(false);

        ResponseEntity<String> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(productService).deleteProduct(1L);
    }
}
