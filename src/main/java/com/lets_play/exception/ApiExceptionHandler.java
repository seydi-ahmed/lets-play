package com.lets_play.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // 🔐 Authentification : email ou mot de passe incorrect
    @ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class })
    public ResponseEntity<Object> handleAuthExceptions(Exception ex) {
        return new ResponseEntity<>(
                Map.of("error", "Email ou mot de passe invalide."),
                HttpStatus.UNAUTHORIZED);
    }

    // 🛑 Accès interdit
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>(
                Map.of("error", "🚫 Accès refusé : vous n'avez pas les droits nécessaires pour accéder à cette ressource."),
                HttpStatus.FORBIDDEN);
    }

    // ⚠️ Erreurs de validation via @Valid (ex: champs manquants ou invalides)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(Map.of(
                "error", "Validation échouée",
                "details", errors
        ), HttpStatus.BAD_REQUEST);
    }

    // ⚠️ Erreurs de validation dans @RequestParam ou @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> errors.put("param", cv.getMessage()));

        return new ResponseEntity<>(Map.of(
                "error", "Paramètres invalides",
                "details", errors
        ), HttpStatus.BAD_REQUEST);
    }

    // 🔁 Conflit (ex: email déjà utilisé)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceExists(ResourceAlreadyExistsException ex) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    // 🔍 Ressource introuvable
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    // 💥 Fallback : toute autre erreur non gérée explicitement
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                Map.of("error", "💥 Erreur interne : " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
