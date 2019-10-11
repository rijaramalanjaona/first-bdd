package dev.rija.adresse;

import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;
import dev.rija.entities.Contrat;
import dev.rija.entities.Operation;
import dev.rija.helpers.AdresseHelper;
import dev.rija.services.AbonneService;
import dev.rija.services.AbonneServiceImpl;
import net.thucydides.core.annotations.Step;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StepdefsWithSerenity {

    private Operation operation;
    private Abonne abonne;
    private Adresse adressePrincipale;

    private AbonneService abonneService = new AbonneServiceImpl();

    private int nombreModification = 0;

    @Step("^un abonné avec une adresse principale (.*) en (.*)$")
    public void un_abonné_avec_une_adresse_principale_active_ou_inactive_dans_un_pays(String active, String pays) {
        abonne = new Abonne();
        adressePrincipale = new Adresse();
        adressePrincipale.setActive(AdresseHelper.isAdresseActive(active));
        adressePrincipale.setPays(pays);
        abonne.setAdressePrincipale(adressePrincipale);
    }

    @Step("^le conseiller connecté à (.*) modifie l'adresse de l'abonné (.*)$")
    public void le_conseiller_connecté_à_canal_modifie_l_adresse_de_l_abonné_avec_condition(String canal, String condition) {
        operation = new Operation();
        operation.setCanalConnexion(canal);
        operation.setNom("modification_adresse_abonne");
        operation.setAbonneConcerne(abonne);
        operation.setSansDateEffet(AdresseHelper.sansDateEffet(condition));
        abonneService.save(abonne);
        nombreModification++;
    }

    @Step("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
    public void l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné() {
        List<Contrat> contrats = abonne.getContrats();
        if (operation.isSansDateEffet()) {
            nombreModification += abonneService.modifierAdresseContratsSansDateEffet(abonne, adressePrincipale);
            for (Contrat contrat: contrats) {
                assertThat(contrat.getAdresse(), is(adressePrincipale));
            }
        } else {
            nombreModification += abonneService.modifierAdresseContratsAvecDateEffet(abonne, adressePrincipale);
            for (Contrat contrat: contrats) {
                assertThat(contrat.getAdresseUlterieure(), is(adressePrincipale));
            }
        }

    }

    @Step("^un mouvement de modification d'adresse est créé$")
    public void un_mouvement_de_modification_d_adresse_est_créé() {
        assertTrue(nombreModification > 0);
    }
}
