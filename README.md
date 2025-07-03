# 🎮 lets-play

Un projet Java Spring Boot avec MongoDB et JWT pour gérer un système CRUD de produits et d'utilisateurs avec gestion des rôles (`USER`, `ADMIN`).

---

## 🚀 Fonctionnalités

- ✅ **Authentification avec JWT**
- ✅ **Enregistrement / Connexion d'utilisateurs**
- ✅ **Création de produits liée à l’utilisateur connecté**
- ✅ **CRUD produit (avec vérification de rôle et de propriété)**
- ✅ **CRUD utilisateur (accessible uniquement à l’ADMIN)**
- ✅ **Sécurité par rôle (`ROLE_USER`, `ROLE_ADMIN`)**
- ✅ **API RESTful avec Spring Boot + MongoDB**

---

## 🛠️ Stack Technique

- Java 17
- Spring Boot
- Spring Security (avec JWT)
- MongoDB
- Lombok
- Maven
- Postman ou cURL (pour test)

---

## 📁 Structure du projet

```bash
lets-play/
├── controller/       # Contrôleurs REST : Auth, Product, User
├── model/            # Modèles MongoDB : User, Product, Role
├── service/          # Logique métier
├── repository/       # Accès MongoDB
├── config/           # Configuration de sécurité
├── security/         # JWT filter + service
└── dto/              # Objets de transfert : login/register
```

---

Installation locale
1. Cloner le repo
```
git clone https://github.com/seydi-ahmed/lets-play.git
cd lets-play
```

2. Lancer MongoDB:
- Assurez-vous que MongoDB est en cours d'exécution localement (port par défaut : 27017) --> ```sudo systemctl status mongod```
- Démarrer MongoDB --> ```sudo systemctl start mongod```

3. Lancer le projet
```
./mvnw spring-boot:run
```
- L’API démarre sur http://localhost:8080

---

## 🔐 Authentification

| Méthode | Route                | Description                  |
| ------- | -------------------- | ---------------------------- |
| POST    | `/auth/register`     | Inscription d’un utilisateur |
| POST    | `/auth/authenticate` | Connexion (génère un token)  |

- Header requis pour accéder aux routes sécurisées:
```
Authorization: Bearer <JWT_TOKEN>
```

---
## 👤 Rôles

| Rôle         | Droits                                                |
| ------------ | ----------------------------------------------------- |
| `ROLE_USER`  | Peut créer/lire ses produits                          |
| `ROLE_ADMIN` | Peut tout faire (produits + gestion des utilisateurs) |


---

## 📦 Endpoints Produits

| Méthode | Route                | Accès         | Description                                |
| ------- | -------------------- | ------------- | ------------------------------------------ |
| POST    | `/api/products`      | USER, ADMIN   | Créer un produit                           |
| GET     | `/api/products/my`   | USER, ADMIN   | Voir ses propres produits                  |
| GET     | `/api/products`      | ADMIN         | Voir tous les produits                     |
| GET     | `/api/products/{id}` | USER\*, ADMIN | Voir un produit (si propriétaire ou admin) |
| PUT     | `/api/products/{id}` | USER\*, ADMIN | Modifier un produit                        |
| DELETE  | `/api/products/{id}` | USER\*, ADMIN | Supprimer un produit                       |
| DELETE  | `/api/products`      | ADMIN         | Supprimer tous les produits                |


---

## 👥 Endpoints Utilisateurs (ADMIN ONLY)

| Méthode | Route                      | Description                     |
| ------- | -------------------------- | ------------------------------- |
| GET     | `/api/users`               | Voir tous les utilisateurs      |
| GET     | `/api/users/{id}`          | Voir un utilisateur par ID      |
| GET     | `/api/users/email/{email}` | Rechercher par email            |
| POST    | `/api/users`               | Créer / mettre à jour un user   |
| DELETE  | `/api/users/{id}`          | Supprimer un utilisateur        |
| DELETE  | `/api/users`               | Supprimer tous les utilisateurs |


---

---

## Auteur
- **Nom** : Mouhamed DIOUF
- **GitHub** : [mouhameddiouf](https://github.com/seydi-ahmed)
- **Email** : seydiahmedelcheikh@gmail.com
- **Numéro** : +221776221681
- **Réseaux** : [LinkedIn](https://linkedin.com/in/mouhamed-diouf-435207174)