package com.lets_play.service;

import com.lets_play.exception.ResourceAlreadyExistsException;
import com.lets_play.exception.ResourceNotFoundException;
import com.lets_play.model.Role;
import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new ResourceAlreadyExistsException("Un utilisateur avec cet email existe déjà.");
        });

        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Défaut = USER
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
    }

    public void deleteById(String id, User currentUser) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Suppression impossible : aucun utilisateur trouvé avec l'ID : " + id);
        }

        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException(
                    "🚫 Accès refusé : seuls les administrateurs peuvent supprimer un utilisateur.");
        }

        userRepository.deleteById(id);
    }

    public User updateUser(User updatedUser, User currentUser) {
        User existing = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur à mettre à jour introuvable"));

        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException(
                    "🚫 Accès refusé : seuls les administrateurs peuvent modifier un utilisateur.");
        }

        // 🔁 Mise à jour des champs
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setRole(updatedUser.getRole());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            // Si le mot de passe est déjà chiffré, on ne le re-chiffre pas
            if (!updatedUser.getPassword().startsWith("$2a$")) {
                existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            } else {
                existing.setPassword(updatedUser.getPassword());
            }
        }

        return userRepository.save(existing);
    }

    public void deleteAllUsers(User currentUser) {
        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Seuls les administrateurs peuvent supprimer tous les utilisateurs.");
        }

        userRepository.deleteAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
