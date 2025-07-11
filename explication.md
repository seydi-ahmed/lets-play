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
17. MongoRepository<User, String>
- répertoire fourni par Spring Data Mongo
- permet de CRUD sur la collection (table) MongoDB qui correspond à la classe User
- <User, String>
    - User correspond à l'entité (model)
    - String correspond au type de la clé primaire
18. @Service: identifie une classe comme un service métier
- cela permet à spring de gérer son cycle de vie et de l'injecter automatiquement dans d'autres classes (comme dans les controllers)
19. Le contrôleur appelle les services pour accéder aux données, au lieu d’accéder directement au repository.
20. @Autowired: demande à spring d'injecter automatiquement une instance ProductRepository via le constructeur
21. @Valid: déclanche la validation automatique
22. ResponseEntity: permet d'envoyer une réponse HTTP compléte (statut + corps)
23. Optional: permet de gérer la présence ou l'absence d'un Objet
24. @PathVariable: lit l'id de l'url
25. BCryptPasswordEncoder: utiliser l'algorithme BCrypt pour encoder les mots de passes
26. @SpringBootApplication: @Configuration + @EnableAutoConfiguration + @ComponentScan --> point d'entrée pricipale de notre application Spring Boot
27. @Configuration: contient les Bean Spring
28. @EnableAutoConfiguration: active la configuration automatique de Spring Boot
29. @ComponentScan: recherche les composants dans le package et sous package
30. CommandLineRunner: lorsqu'on veut exécuter une commande juste aprés le démarrage de notre application
31. jakarta.validation: utiliser pour valider les entrées d'utilisateurs
32. @NoArgsConstructor: génére le constructeur par défaut
33. @AllArgsConstructor: génére un contructeur avec initialisations
34. @RequiredArgsConstructor: génére un constructeur avec tous les champs "final" injectés
35. jwtService: crée un token pour l'utilisateur
36. authenticationManager: vérifie si l'email + Mot de passe sont valides
37. 

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

## Commandes
- keytool -genkeypair -alias letsplay -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/letsplay.p12 -validity 365 -storepass changeit
- --> pour la sécurités (HTTP --> HTTPS)

---

---