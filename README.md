# first-bdd

## 1) Implémenter scénario Cucumber

- Créer un projet Spring Boot dans IntelliJ

- Ajouter les dépendances maven pour cucumber

- Créer un source folder "src/test/resources"

- Créer le fichier adresse.feature dans src/test/resources/features avec le contenu fourni dans le sujet de l'exercice

- Créer le lanceur de test : /src/test/java/dev/rija/adresse/cucumber/RunCucumberTest.java

- Executer RunCucumberTest.java

- Récupérer dans la console les squelettes de step à implémenter dans /src/test/java/dev/rija/adresse/cucumber/Stepdefs.java

- Faire quelques ajustements au niveau des syntaxes des squelettes de step récupérés pour traiter tous les cas

- Implémenter les steps

- Implémenter le code métier

## 2) Rapport des résultats avec serenity

- Ajouter les dépendances et plugins maven pour serenity

- Créer un lanceur de test : /src/test/java/dev/rija/adresse/serenity/RunWithSerenityTest.java

- Dans RunWithSerenityTest.java, ajouter un Serenity step library : @Steps StepdefsWithSerenity stepdefsWithSerenity

- Dans RunWithSerenityTest.java, ajouter une méthode @Test public void modifier_adresse_abonne()

- Le fichier StepdefsWithSerenity.java contient tous les steps

- Implémenter les steps

- Executer RunWithSerenityTest.java

- Ouvrir dans un navigateur le rapport i.e le fichier html généré dans le target/site/serenity


## 3) Simuler les appels webserver APIs avec WireMock

- Ajouter les dépendances maven pour wiremock

- Créer un fichier src/test/resources/data/abonne.json qui reprend la structure d'un Abonne

- Créer le lanceur de test : /src/test/java/dev/rija/adresse/web/WebApiTest.java

- Créer le fichier /src/test/java/dev/rija/adresse/web/Stepdefs.java contenant les steps

- Simuler l'appel GET de l'API /abonne/1 dans @Etantdonné("^un abonné avec une adresse principale (.*) en (.*)$")

- Simuler l'appel PUT de l'API /abonne/1 dans @Lorsque("^le conseiller connecté à (.*) modifie l'adresse de l'abonné (.*)$")
