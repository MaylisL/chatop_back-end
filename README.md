# Chatop Rental API

API REST pour la gestion de locations immobilières (utilisateurs, locations, messages).  
Projet développé avec **Spring Boot**, **Spring Security (JWT)**, **JPA/Hibernate** et **MySQL**.  
La documentation de l’API est disponible via **Swagger**.

---
## Installation et lancement

### Prérequis

* Java 17
* Spring Boot 3.5
* Spring Security (JWT)
* Hibernate / JPA
* Lombok
* MySQL
* Swagger / OpenAPI 3

### 1. Cloner le projet

git clone le repository

cd chatop

### 2. Configurer la base de données

Créer une base MySQL:
```
CREATE DATABASE chatop;
```

vous pouvez trouver le schéma de la base de données en suivant ce lien: [schéma BDD](https://github.com/MaylisL/Developpez-le-back-end-en-utilisant-Java-et-Spring/blob/main/ressources/sql/script.sql)

### 3. Configurer les variables d'environnement

Il faut configurer dans l'IDE les différentes variables d'environnements de application.properties et de RentalServices

`MYSQL_USERNAME` : utilisateur MySQL

`MYSQL_PASSWORD` : mot de passe MySQL

`JWT_KEY` : clé secrète pour signer les tokens JWT

`SERVER_URL` : URL du backend pour servir les images de rental 

Dans application.properties:
```
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
JWT_KEY=${JWT_KEY}
```
Dans RentalService:
```
SERVER_URL=${SERVER_URL}
```
### 4. Lancer l'application

Avec Maven wrapper:
```
./mvnw spring-boot:run
```

Avec Maven installé:
```
mvn spring-boot:run
```

L'application va se lancer sur `http://localhost:8080`

### 5. Documentation Swagger

Une fois l’application démarrée, la documentation interactive Swagger est accessible via l'url :
`http://localhost:8080/swagger-ui.html`


