package dev.rija.services;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;
import dev.rija.entities.Archive;
import dev.rija.entities.Contrat;

import java.util.List;

public class AbonneServiceImpl implements AbonneService {

    @Override
    public Abonne save(Abonne abonne) {
        // TODO persitance
        return abonne;
    }

    @Override
    public void modifierAdresseContratsSansDateEffet(Abonne abonne, Adresse nouvelleAdresse) {
        List<Contrat> contrats = abonne.getContrats();
        for (Contrat contrat: contrats) {
            contrat.setAdresse(nouvelleAdresse);
        }
        // TODO persitance
    }

    @Override
    public void modifierAdresseContratsAvecDateEffet(Abonne abonne, Adresse nouvelleAdresse) {
        List<Contrat> contrats = abonne.getContrats();
        for (Contrat contrat: contrats) {
            contrat.setAdresseUlterieure(nouvelleAdresse);
        }
        // TODO persitance
    }

    @Override
    public void modifierArchives(Abonne abonne, Adresse nouvelleAdresse) {
        List<Archive> archives = abonne.getArchives();
        for (Archive archive : archives) {
            archive.setAdresse(nouvelleAdresse);
        }
        // TODO persitance
    }
}
