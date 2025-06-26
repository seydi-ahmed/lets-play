package com.lets_play.service;

import com.lets_play.model.Product;
import com.lets_play.model.User;
import com.lets_play.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthService authService;

    public Product createProduct(Product product) {
        User currentUser = authService.getCurrentUser();
        product.setUserId(currentUser.getId());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsForCurrentUser() {
        User currentUser = authService.getCurrentUser();
        return productRepository.findByUserId(currentUser.getId());
    }

    public Product getProductByIdWithOwnershipCheck(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        User currentUser = authService.getCurrentUser();

        if (isAdmin(currentUser) || product.getUserId().equals(currentUser.getId())) {
            return product;
        }

        throw new AccessDeniedException("Accès refusé");
    }

    public Product updateProductWithOwnershipCheck(String id, Product updatedProduct) {
        Product existing = getProductByIdWithOwnershipCheck(id);

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());

        return productRepository.save(existing);
    }

    public void deleteProductWithOwnershipCheck(String id) {
        Product existing = getProductByIdWithOwnershipCheck(id);
        productRepository.delete(existing);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    private boolean isAdmin(User user) {
        return user.getRole().name().equals("ROLE_ADMIN");
    }
}
