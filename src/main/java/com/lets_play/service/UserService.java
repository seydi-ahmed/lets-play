package com.lets_play.service;

import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Injection de UserRepository + PasswordEncoder
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔐 Méthode pour créer ou mettre à jour un utilisateur (encodage du mot de passe)
    public User save(User user) {
        if (!user.getPassword().startsWith("$2a$")) { // Évite de re-hasher un mot de passe déjà encodé
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Méthode pour récupérer un utilisateur par son id
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }


    // Méthode pour supprimer un utilisateur par son id
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    // Méthode pour supprimer les utilisateurs
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Recherche utilisateur par email (utile pour l’authentification)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
