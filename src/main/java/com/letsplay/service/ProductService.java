package com.letsplay.service;

import com.letsplay.model.Product;
import com.letsplay.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByUserId(String userId) {
        return productRepository.findByUserId(userId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
    
    public Product updateProduct(String id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

}