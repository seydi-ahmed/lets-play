package com.lets_play.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        // Exemple d'URL bloquées spécifiques
        String uri = request.getRequestURI();
        String message;

        if (uri.contains("/api/users") && request.getMethod().equals("DELETE")) {
            message = "🚫 Seuls les administrateurs peuvent supprimer des utilisateurs.";
        } else if (uri.contains("/api/products")) {
            message = "🚫 Vous n’avez pas la permission d’accéder ou de modifier ce produit.";
        } else {
            message = "🚫 Accès refusé : vous n'avez pas les droits nécessaires pour accéder à cette ressource.";
        }

        // Corps de la réponse JSON
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("status", 403);
        responseBody.put("error", "Forbidden");
        responseBody.put("message", message);
        responseBody.put("path", uri);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
