package com.lets_play.controller;

import com.lets_play.model.User;
import com.lets_play.service.AuthService;
import com.lets_play.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // 🔸 Créer un utilisateur
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
        try {
            User saved = userService.save(user);
            return ResponseEntity.ok("✅ Utilisateur enregistré avec succès : " + saved.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Erreur : " + e.getMessage());
        }
    }

    // 🔸 Récupérer tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 🔸 Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        return user.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("❌ Utilisateur introuvable avec l'ID : " + id));
    }

    // 🔸 Modifier un utilisateur
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
        try {
            User currentUser = authService.getCurrentUser();
            User updated = userService.updateUser(user, currentUser);
            // return ResponseEntity.ok("✏️ Utilisateur mis à jour avec succès : " + updated.getEmail());
            return ResponseEntity.ok(updated);

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Erreur : " + e.getMessage());
        }
    }

    // 🔸 Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        try {
            User currentUser = authService.getCurrentUser();
            userService.deleteById(id, currentUser);
            return ResponseEntity.ok("🗑️ Utilisateur supprimé avec succès (ID : " + id + ")");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("❌ " + e.getMessage());
        }
    }

    // 🔸 Supprimer tous les utilisateurs
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        try {
            User currentUser = authService.getCurrentUser();
            userService.deleteAllUsers(currentUser);
            return ResponseEntity.ok("🧹 Tous les utilisateurs ont été supprimés par l'administrateur.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("🚫 Accès refusé : seuls les administrateurs peuvent supprimer tous les utilisateurs.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("💥 Erreur interne : " + e.getMessage());
        }
    }

    // 🔸 Récupérer un utilisateur par email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("❌ Utilisateur introuvable avec l'email : " + email));
    }
}
