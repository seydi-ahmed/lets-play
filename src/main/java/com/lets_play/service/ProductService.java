package com.lets_play.service;

import com.lets_play.exception.ResourceNotFoundException;
import com.lets_play.model.Product;
import com.lets_play.model.Role;
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
        product.setId(null); // forcer la création
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
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'ID : " + id));

        User currentUser = authService.getCurrentUser();

        if (isAdmin(currentUser) || isOwner(product, currentUser)) {
            return product;
        }

        throw new AccessDeniedException("Vous n'avez pas les droits d'accès à ce produit.");
    }

    public Product updateProductWithOwnershipCheck(String id, Product updatedProduct) {
        Product existing = getProductByIdWithOwnershipCheck(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        return productRepository.save(existing);
    }

    public void deleteProductWithOwnershipCheck(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable avec l'ID : " + id));

        User currentUser = authService.getCurrentUser();

        if (!isAdmin(currentUser) && !isOwner(product, currentUser)) {
            throw new AccessDeniedException("Vous ne pouvez pas supprimer ce produit.");
        }

        productRepository.delete(product);
    }

    public void deleteAllProducts() {
        User currentUser = authService.getCurrentUser();

        if (!isAdmin(currentUser)) {
            throw new AccessDeniedException("Seul un administrateur peut supprimer tous les produits.");
        }

        productRepository.deleteAll();
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ROLE_ADMIN;
    }

    private boolean isOwner(Product product, User user) {
        return product.getUserId().equals(user.getId());
    }
}
