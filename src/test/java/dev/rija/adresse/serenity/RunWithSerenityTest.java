package dev.rija.adresse.serenity;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

@RunWith(SerenityParameterizedRunner.class)
public class RunWithSerenityTest {
    private String canal;
    private String active;
    private String pays;
    private String condition;

    @TestData
    public static Collection<Object[]> testData(){
        return Arrays.asList(new Object[][]{
                {"FACE", "inactive", "France", "sans date d'effet"},
                {"EC", "active", "Pologne", "avec date d'effet"}
        });
    }

    public RunWithSerenityTest(String canal, String active, String pays, String condition) {
        this.canal = canal;
        this.active = active;
        this.pays = pays;
        this.condition = condition;
    }

    @Steps
    StepdefsWithSerenity stepdefsWithSerenity;

    @Test
    public void modifier_adresse_abonne() {
        stepdefsWithSerenity.un_abonné_avec_une_adresse_principale_active_ou_inactive_dans_un_pays(active, pays);

        stepdefsWithSerenity.le_conseiller_connecté_à_canal_modifie_l_adresse_de_l_abonné_avec_condition(canal, condition);

        stepdefsWithSerenity.l_adresse_de_l_abonné_modifiée_est_enregistrée_sur_l_ensemble_des_contrats_de_l_abonné();

        stepdefsWithSerenity.un_mouvement_de_modification_d_adresse_est_créé();

    }
}
