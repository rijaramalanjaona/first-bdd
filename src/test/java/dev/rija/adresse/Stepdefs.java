package dev.rija.adresse;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;
import dev.rija.entities.Contrat;
import dev.rija.entities.Operation;
import dev.rija.helpers.AdresseHelper;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Lorsque;

import java.util.List;

public class Stepdefs {
    private Operation operation;
    private Abonne abonne;
    private Adresse adressePrincipale;

    @Etantdonné("^un abonné avec une adresse principale (.*) en (.*)$")
    public void un_abonné_avec_une_adresse_principale_active_ou_inactive_dans_un_pays(String active, String pays) {
        abonne = new Abonne();
        adressePrincipale = new Adresse();
        adressePrincipale.setActive(AdresseHelper.isAdresseActive(active));
        adressePrincipale.setPays(pays);
        abonne.setAdressePrincipale(adressePrincipale);
    }

    @Lorsque("^le conseiller connecté à (.*) modifie l'adresse de l'abonné (.*)$")
    public void le_conseiller_connecté_à_canal_modifie_l_adresse_de_l_abonné_avec_condition(String canal, String condition) {
       operation = new Operation();
       operation.setCanalConnexion(canal);
       operation.setAbonneConcerne(abonne);
       operation.setSansDateEffet(AdresseHelper.sansDateEffet(condition));
    }

    @Alors("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
    public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() {
        List<Contrat> contrats = abonne.getContrats();
        if (operation.isSansDateEffet()) {
            // assertion
        }
    }

    @Alors("^un mouvement de modification d'adresse est créé$")
    public void un_mouvement_de_modification_d_adresse_est_créé() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}
