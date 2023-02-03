# Plat du Jour

Partie serveur de l'application mobile permettant d'afficher tous les plats du jour des restaurants autour de ma position.
 

## Stack technique

- Base de données NoSQL: **MongoDB**
- Framework backend: **Spring Boot**
- Protocole communication client/server: **GraphQL**
- Sécurité: **Spring Security**


## Installation
1. Installer MongoDB Server à partir de https://www.mongodb.com/download-center/community
2. Cloner le projet **ppj-backend**
3. Se positionner à la racine du projet `cd ppj-backend` et installer le projet: `mvn clean install`
4. Démarrer le projet **ppj-apiserver**: `mvn spring-boot:run` ou directement en application Spring Boot
5. Une fois le serveur démarré, accéder au graphiql en localhost sur le port 40027: http://localhost:40027/graphiql
6. Exécuter une requête graphql pour vérifier que le serveur communique correctement avec la base Mongo:
 
 ```
query {
      events {
        description
        availableOn
        place {
          name
          address {
            street
            zipCode
            city
            location
          }
        }
      }
    }
```

  
## Génération de fake emplacements/évènements (en localhost)

1. Modifier le fichier *application-local.properties* pour y changer les propriétés:
	- **position.lat**: la latitude de votre position
	- **position.lng**: la longitude de votre position
	- **position.distance**: le rayon (en mètres) dans lequel seront générés les emplacements
	- **fakes.places**: le nombre de faux emplacements à générer
	- **fakes.events**: le nombre de faux évènements à générer
	- **fakes.tags**: le nombre de faux tags à générer
2. Lancer le projet via la commande `mvn spring-boot:run -Plocal`
