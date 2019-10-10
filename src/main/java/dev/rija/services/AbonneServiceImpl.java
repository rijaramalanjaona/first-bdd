package dev.rija.services;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;
import dev.rija.entities.Contrat;

import java.util.List;

public class AbonneServiceImpl implements AbonneService {
    public Abonne save(Abonne abonne) {
        // TODO persitance
        return abonne;
    }

    public int modifierAdresseContratsSansDateEffet(Abonne abonne, Adresse nouvelleAdresse) {
        List<Contrat> contrats = abonne.getContrats();
        for (Contrat contrat: contrats) {
            contrat.setAdresse(nouvelleAdresse);
        }
        // TODO persitance
        return contrats.size();
    }

    public int modifierAdresseContratsAvecDateEffet(Abonne abonne, Adresse nouvelleAdresse) {
        List<Contrat> contrats = abonne.getContrats();
        for (Contrat contrat: contrats) {
            contrat.setAdresseUlterieure(nouvelleAdresse);
        }
        // TODO persitance
        return contrats.size();
    }
}
