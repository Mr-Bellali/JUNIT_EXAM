# Examen JUnit

## Structure du projet
Il s'agit d'un projet simple de système de reservation ; nous nous sommes concentrés uniquement sur la fonctionnalité de réservation, car c'est la fonctionnalité principale.
```
.
├── Diagramme de classe.png
├── README.md
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── devops
    │   │           ├── Main.java
    │   │           ├── Order.java
    │   │           ├── Product.java
    │   │           ├── ReservationService.java
    │   │           └── Student.java
    │   └── resources
    └── test
        └── java
            └── com
                └── devops
                    ├── ProductTest.java
                    └── ReservationServiceTest.java
```

## Diagramme de classes
![Diagramme de classes](Diagramme%20de%20classe.png)

Vous remarquerez que nous n'avons pas modélisé la classe du service de réservation, car ce diagramme a été conçu uniquement à partir des entités de données qui nous ont été fournies.

## Tests unitaires

### Tests du service de réservation
C'est le cœur des tests, puisqu'il s'agit de la fonctionnalité principale. Nous avons suivi un ensemble de règles qui nous ont été fournies pour écrire les tests unitaires, et, afin de bien isoler la classe testée, nous avons utilisé Mockito pour simuler les autres classes avec lesquelles elle interagit (Student, Product).

### Tests de Product
Ces tests sont moins étoffés que ceux du service de réservation, mais ils étaient demandés dans les règles.