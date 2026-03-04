# 🎬 VOD — Application de Gestion de Vidéos à la Demande

Application Web complète de gestion de réservations de films (VOD) développée dans le cadre du projet de Master 1 ILIADE/TIIL-A à l'UBO.
L'application est construite sur une architecture microservices avec un frontend Vue.js et plusieurs API REST (Spring Boot + Servlets).

**Auteurs :** Timothée BENCHERGUI - Léa CRENN-DURIF - Maxence THOMAS

---

## 📌 Description

L'application permet de gérer des réservations de films à la demande. Elle offre :

* Inscription et connexion des utilisateurs (pseudo unique, informations personnelles)
* Consultation des films, acteurs, réalisateurs et genres
* Réservation de films (max 3 simultanément, avec vérification d'âge)
* Paiement simulé par carte bancaire pour finaliser une réservation
* Évaluation des films (note 0-5 + commentaire facultatif)
* Gestion des affiches de films (stockées en MongoDB)
* Interface d'administration : création de films/artistes, ouverture/fermeture à la location, modification du prix

L'application s'appuie sur l'écosystème de microservices suivant :

* **gateway** — point d'entrée unique (API Gateway)
* **user_api** — gestion des utilisateurs
* **reservation_api** — gestion des réservations
* **payment_api** — paiement simulé par carte bancaire
* **film_api** — gestion des films, acteurs, réalisateurs (Spring)
* **review_api** — évaluations et affiches (Servlets + MongoDB)
* **frontend** — interface client Vue.js

---

## 🧱 Stack technique

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven
* Base de données SQL (MariaDB)
* MongoDB (affiches + évaluations)
* Vue.js (frontend)
* Nginx (serveur statique frontend)
* Swagger / OpenAPI
* Docker / Docker Compose

---

## 🚀 Lancement du projet

### 1) Cloner le repository

```bash
git clone git@github.com:timo0135/M1-UBO-Spring-Boot-Testing-App.git
cd streaming-project
```

### 2) Lancer avec Docker Compose

```bash
docker compose up -d
```

### 3) URL par défaut

```
http://localhost:8080   → API Gateway
http://localhost:5173   → Frontend Vue.js
```

---

## 📂 Structure du projet

```
streaming-project/
│
├── frontend/               # Interface client Vue.js
│
├── API-gateway/            # Spring Cloud Gateway
│
├── API-payment/            # Microservice paiement (Spring Boot + MariaDB)
│
├── docker-compose.yml
└── README.md
```

---

## 📡 Endpoints principaux

### Payment API

| Champ   | Type | Description             |
| ------- | ---- | ----------------------- |
| id      | Long | Identifiant du paiement |
| montant | int  | Montant du paiement     |
| status  | int  | Statut du paiement      |

### Statuts de paiement

| Code | Signification |
| ---- | ------------- |
| 0    | En attente    |
| 1    | Validé        |
| 2    | Refusé        |
| 3    | Remboursé     |

---

## 🗄 Configuration base de données

Exemple avec MariaDB dans `application.properties` :

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/payement_db
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

---

## 📘 Documentation Swagger

Disponible après lancement :

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📄 Licence

Licence Open Source (MIT License).
