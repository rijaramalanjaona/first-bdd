package dev.rija.services;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;
import dev.rija.entities.Contrat;

import java.util.List;

public class AbonneServiceImpl implements AbonneService {

    public int modifierAdresseContratsSansDateEffet(Abonne abonne, Adresse nouvelleAdresse) {
        List<Contrat> contrats = abonne.getContrats();
        for (Contrat contrat: contrats) {
            contrat.setAdresse(nouvelleAdresse);
        }
        return contrats.size();
    }

    public int modifierAdresseContratsAvecDateEffet(Abonne abonne, Adresse nouvelleAdresse) {
        List<Contrat> contrats = abonne.getContrats();
        for (Contrat contrat: contrats) {
            contrat.setAdresseUlterieure(nouvelleAdresse);
        }
        return contrats.size();
    }
}
