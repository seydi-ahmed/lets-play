package com.lets_play.controller;

import com.lets_play.model.User;
import com.lets_play.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')") // 👈 Protection globale
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔸 Créer ou mettre à jour un utilisateur
    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    // 🔸 Récupérer tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 🔸 Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // 🔸 Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 🔸 Supprimer les utilisateurs
    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    // 🔸 (optionnel) Récupérer un utilisateur par email — peut être utile pour debug ou login test
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}
