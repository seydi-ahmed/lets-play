package com.lets_play.controller;

import com.lets_play.model.Product;
import com.lets_play.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ✅ Création par USER ou ADMIN
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    // ✅ ADMIN : voir tous les produits
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ✅ USER : voir ses produits
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> getMyProducts() {
        return ResponseEntity.ok(productService.getProductsForCurrentUser());
    }

    // ✅ Voir un produit (ADMIN ou propriétaire uniquement)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductByIdWithOwnershipCheck(id));
    }

    // ✅ Modifier un produit (ADMIN ou propriétaire uniquement)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProductWithOwnershipCheck(id, product));
    }

    // ✅ Supprimer un produit (ADMIN ou propriétaire uniquement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProductWithOwnershipCheck(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Supprimer tous les produits (ADMIN uniquement)
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllProducts() {
        productService.deleteAllProducts();
        return ResponseEntity.noContent().build();
    }
}
