package com.lets_play.service;

import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection — recommandé pour Spring
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Méthode pour récupérer un utilisateur par son id
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    // Méthode pour créer ou mettre à jour un utilisateur
    public User save(User user) {
        return userRepository.save(user);
    }

    // Méthode pour supprimer un utilisateur par son id
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    // Recherche utilisateur par email (utile pour l’authentification)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
