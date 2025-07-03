package com.lets_play.controller;

import com.lets_play.exception.ResourceNotFoundException;
import com.lets_play.model.Product;
import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;
import com.lets_play.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    // 🔸 Créer un produit
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        product.setUserId(user.getId());
        productService.createProduct(product);
        return ResponseEntity.ok("✅ Produit créé avec succès.");
    }

    // 🔸 Voir tous les produits
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 🔸 Voir ses produits
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Product>> getMyProducts() {
        return ResponseEntity.ok(productService.getProductsForCurrentUser());
    }

    // 🔸 Voir un produit par ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            Product product = productService.getProductByIdWithOwnershipCheck(id);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("❌ Produit non trouvé avec l'ID : " + id);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("🚫 Accès refusé : vous n'avez pas le droit de voir ce produit.");
        }
    }

    // 🔸 Modifier un produit
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
        try {
            productService.updateProductWithOwnershipCheck(id, product);
            return ResponseEntity.ok("✅ Produit mis à jour avec succès.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("❌ Produit introuvable avec l'ID : " + id);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("🚫 Accès refusé : vous ne pouvez pas modifier ce produit.");
        }
    }

    // 🔸 Supprimer un produit
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProductWithOwnershipCheck(id);
            return ResponseEntity.ok("🗑️ Produit supprimé avec succès.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("❌ Produit introuvable avec l'ID : " + id);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("🚫 Accès refusé : vous ne pouvez pas supprimer ce produit.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("💥 Erreur interne : " + e.getMessage());
        }
    }

    // 🔸 Supprimer tous les produits (ADMIN uniquement)
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<String> deleteAllProducts() {
        try {
            productService.deleteAllProducts();
            return ResponseEntity.ok("🧹 Tous les produits ont été supprimés par un administrateur.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("🚫 Accès refusé : seuls les administrateurs peuvent supprimer tous les produits.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("💥 Erreur interne : " + e.getMessage());
        }
    }
}
