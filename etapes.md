# Les Étapes du projet

---

## 🟢 Étape 1 – Préparer l’environnement
- Installer Java (17 ou 21), Maven, un IDE comme IntelliJ ou VSCode.
- Installer MongoDB en local ou utiliser MongoDB Atlas.
- Dézipper le projet téléchargé depuis Spring Initializr, l’ouvrir dans ton IDE.

---

## 🟡 Étape 2 – Configurer la connexion MongoDB
- Modifier le application.properties ou application.yml avec l'URI MongoDB.
- Tester la connexion avec une simple entité + repository + endpoint de test.

---

## 🟡 Étape 3 – Créer les entités User et Product
- Créer deux classes avec les champs décrits dans ton diagramme.
- Utiliser les annotations @Document, @Id, @Field de Spring Data MongoDB.
- Ajouter des validations : @NotNull, @Email, @Size, etc.

---

## 🟡 Étape 4 – Repositories
- Créer deux interfaces UserRepository et ProductRepository qui étendent MongoRepository.

---

## 🟡 Étape 5 – Services
- Créer des services (UserService, ProductService) pour isoler la logique métier.
- Ajouter les méthodes CRUD.

---

## 🟡 Étape 6 – Contrôleurs REST
- Créer deux contrôleurs : UserController, ProductController.
- Annoter avec @RestController, @RequestMapping, etc.
- Ajouter les endpoints CRUD.
- Le endpoint GET /products doit être public (pas besoin d’être authentifié).

---

## 🛡️ Étape 7 – Authentification & Sécurité
- Implémenter JWT (token-based authentication).
- Encoder les mots de passe avec BCryptPasswordEncoder.
- Configurer SecurityFilterChain, AuthenticationManager, etc.
- Gérer les rôles (ROLE_USER, ROLE_ADMIN) avec @PreAuthorize.

---

## 🔴 Étape 8 – Gestion des erreurs
- Utiliser un @ControllerAdvice global pour capturer les exceptions.
- Retourner des réponses propres : 400, 404, 401, etc., avec des messages clairs.
- Empêcher les erreurs 500 autant que possible.

---

## 🔐 Étape 9 – Sécuriser l’application
- Ne jamais renvoyer les mots de passe dans les réponses.
- Valider toutes les entrées (injection MongoDB, etc.).
- Obliger HTTPS (dans une vraie prod, mais tu peux juste le documenter pour ce projet).

---

## 🧪 Étape 10 – Tester l’API
- Utilise Postman pour:
- Créer un utilisateur
- Te connecter et obtenir un token
- Créer un produit
- Lire/modifier/supprimer des produits
- Tester les accès selon les rôles
- Accéder à /products sans être authentifié
- Forcer des erreurs pour tester la gestion des exceptions