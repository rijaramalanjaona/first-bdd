package dev.rija.adresse.cucumber;

import dev.rija.entities.*;
import dev.rija.helpers.AdresseHelper;
import dev.rija.services.AbonneService;
import dev.rija.services.AbonneServiceImpl;
import dev.rija.services.OperationService;
import dev.rija.services.OperationServiceImpl;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Lorsque;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Stepdefs {
    // TODO utiliser TestNg dans les assertions
    private Operation operation;
    private Abonne abonne;
    private Adresse adressePrincipale;
    private List<Contrat> contrats;
    private List<Archive> archives;

    private AbonneService abonneService = new AbonneServiceImpl();
    private OperationService operationService = new OperationServiceImpl();

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
        operation.setNom("modification_adresse_abonne");
        operation.setIdAbonne(abonne.getId());
        operation.setSansDateEffet(AdresseHelper.sansDateEffet(condition));
        operationService.save(operation);

        // modification adresse
        abonneService.save(abonne);

        // modification adresse dans les contrats
        if (operation.isSansDateEffet()) {
            abonneService.modifierAdresseContratsSansDateEffet(abonne, adressePrincipale);
        } else {
            abonneService.modifierAdresseContratsAvecDateEffet(abonne, adressePrincipale);
        }

        // modification adresse dans les archives
        abonneService.modifierArchives(abonne, adressePrincipale);
    }

    @Alors("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
    public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() {
        contrats = abonne.getContrats();
        if (operation.isSansDateEffet()) {
            for (Contrat contrat: contrats) {
                assertThat(contrat.getAdresse(), is(adressePrincipale));
            }
        } else {
            for (Contrat contrat: contrats) {
                assertThat(contrat.getAdresseUlterieure(), is(adressePrincipale));
            }
        }
    }

    @Alors("^un mouvement de modification d'adresse est créé$")
    public void un_mouvement_de_modification_d_adresse_est_créé() {
        archives = abonne.getArchives();
        for (Archive archive: archives) {
            assertThat(archive.getAdresse(), is(adressePrincipale));
        }
    }

}
