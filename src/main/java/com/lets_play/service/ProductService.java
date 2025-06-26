package com.lets_play.service;

import com.lets_play.model.Product;
import com.lets_play.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 📌 Indique que cette classe est un "Service" Spring (composant métier injectable)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired // 📌 Injection automatique de la dépendance via le constructeur
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 🔧 Créer un produit
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 🔧 Lire un produit par son ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // 🔧 Lire tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 🔧 Lire les produits d’un utilisateur spécifique
    public List<Product> getProductsByUserId(String userId) {
        return productRepository.findByUserId(userId);
    }

    // 🔧 Mettre à jour un produit
    public Optional<Product> updateProduct(String id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setUserId(updatedProduct.getUserId());
            return productRepository.save(existingProduct);
        });
    }

    // 🔧 Supprimer un produit
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    // 🔧 Supprimer un produit
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
