package dev.rija.services;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;

public interface AbonneService {
    public int modifierAdresseContratsSansDateEffet(Abonne abonne, Adresse nouvelleAdresse);

    public int modifierAdresseContratsAvecDateEffet(Abonne abonne, Adresse nouvelleAdresse);
}
