# language: fr
@adresse @modification
Fonctionnalité: Modifier l'adresse d'un abonné
  @TestRecevabilité
  @ScenarioTest
  Plan du Scénario: Modification de l'adresse d'un abonné résidant en France sans ou avec date d'effet
    Etant donné un abonné avec une adresse principale <active> en <pays>
    Lorsque le conseiller connecté à <canal> modifie l'adresse de l'abonné <condition>
    Alors l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné
    Et un mouvement de modification d'adresse est créé
    Exemples:
      | canal | active    | pays    | condition         |
      | FACE  | inactive  | France  | sans date d'effet |
      | EC    | active    | Pologne | avec date d'effet |
