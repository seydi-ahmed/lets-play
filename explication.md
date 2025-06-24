# Explication

---

## Explication des dépendances:
1. Spring web: pour créer des API REST
2. Spring Data MongoDB: pour connecter et manipuler des bases de données
3. Spring Security: pour gérer l'authentification et l'autorisation
4. Spring Boot DevTools: pour le rechargement à chaud pendant le développement
5. Validation: pour les annotations comme @Email, @NotNull, etc
6. Spring Configuration Processor: pour l'autocomplétion dans les fichiers de configuration
7. Lombok: générer les getters/setters automatiquement
8. @Id: annotation de Spring Data qui marque le champ id comme identifiant unique du document MongoDB.
9. @RestController:
- indique que la classe est un controller REST
- elle gére les requetes HTTP
- toutes les méthodes retournent automatiquement des réponses JSON (comportement de @Controller + @ResponseBody)
10. @RequestMapping("/api/ping"): définit les chemins de base de tous les endpoints (routes)
11. @Bean: permet de créer de beans spring -->
- objet Java géré automatiquement par le conteneur Spring
12. @NotBlank: ne pas être vide
13. @Override: redéfinit une méthode héritée de la classe parent
14. @Data (annotation Lombok): regroupe automatiquement plusieurs fonctionnalités utiles
- exemple: pas besoin d'écrire des getters/setters/toString()/equals()/hashCode()
15. @NoArgsConstructor: génére automatiquement le constructeur par défaut
16. @AllArgsConstructor: génére automatiquement un constructeur avec tous les arguments de la classe
17. 

---

## Définition des termes:
1. API REST: interface qui permet à des applications de communiquer entre elles via des requêtes HTTP
2. Différence entre l'authentification et l'autorisation:
- lorsqu'on vérifie l'identité d'un utilisateur
- lorsqu'on détermine les permissions d'un utilisateur
3. les getters/setters:
- lire la valeur d'un attribut privé
- modifier la valeur d'un attibut privé
4. 

---

---

---