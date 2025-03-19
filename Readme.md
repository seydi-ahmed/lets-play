# Lets-play

## Les étapes du projet
- Configurer le projet Spring Boot avec MongoDB
- Créer les entités (User, Product) et leurs repositories
- Développer les services et controllers pour les opérations CRUD
- Implémenter l'authentification avec JWT
- Gérer les rôles (admin, user) avec Spring Security
- Protéger les endpoints et sécuriser l'application
- Gérer les erreurs et assurer la robustesse de l'API
- Tester avec Postman

## Spring Initializr
- Spring Web (pour les API REST)
- Spring Data MongoDB (pour l'intégration avec MongoDB)
- Spring Security (pour l'authentification et l'autorisation)
- Spring Validation (pour la validation des données)
- Lombok (pour simplifier le code)

## Structure de notre projet
```
src/main/java/com/letsplay
├── config
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── ProductController.java
│   └── UserController.java
├── dto
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── ProductDto.java
│   └── UserDto.java
├── exception
│   └── GlobalExceptionHandler.java
├── model
│   ├── Product.java
│   └── User.java
├── repository
│   ├── ProductRepository.java
│   └── UserRepository.java
├── service
│   ├── AuthService.java
│   ├── ProductService.java
│   └── UserService.java
└── LetsPlayApplication.java
```

## Utilisez docker pour mongo
- sudo apt-get install docker.io (installer docker)
- sudo docker run -d -p 27017:27017 --name mongodb mongo:6.0 (Télécharge et exécute MongoDB avec Docker)
- sudo docker ps (vérifier que mongo est en cours d'exécution)
- spring.data.mongodb.uri=mongodb://localhost:27017/letsplay (Configure Spring Boot pour utiliser MongoDB dans Docker)
- mongosh --host localhost --port 27017