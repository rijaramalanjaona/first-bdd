package dev.rija.services;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;

public interface AbonneService {
    public Abonne save(Abonne abonne);

    public void modifierAdresseContratsSansDateEffet(Abonne abonne, Adresse nouvelleAdresse);

    public void modifierAdresseContratsAvecDateEffet(Abonne abonne, Adresse nouvelleAdresse);

    public void modifierArchives(Abonne abonne, Adresse nouvelleAdresse);
}
