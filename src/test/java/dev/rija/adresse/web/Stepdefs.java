package dev.rija.adresse.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import dev.rija.entities.Abonne;
import dev.rija.entities.Adresse;
import dev.rija.entities.Contrat;
import dev.rija.entities.Operation;
import dev.rija.helpers.AdresseHelper;
import dev.rija.services.AbonneService;
import dev.rija.services.AbonneServiceImpl;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Lorsque;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Stepdefs {
    private Operation operation;
    private Abonne abonne;
    private Adresse adressePrincipale;

    private AbonneService abonneService = new AbonneServiceImpl();

    private int nombreModification = 0;

    WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    CloseableHttpClient httpClient = HttpClients.createDefault();

    String abonneJson;

    @Etantdonné("^un abonné avec une adresse principale (.*) en (.*)$")
    public void un_abonné_avec_une_adresse_principale_active_ou_inactive_dans_un_pays(String active, String pays) throws IOException {
        abonneJson = FileUtils.readFileToString(new File("src/test/resources/data/abonne.json"), StandardCharsets.UTF_8);

        wireMockServer.start();

        // simuler l'appel GET de l'API /abonne/1
        configureFor("localhost", wireMockServer.port());
        stubFor(get(urlEqualTo("/abonne/1"))
                .withHeader("accept", equalTo("application/json"))
                .willReturn(aResponse().withBody(abonneJson)));

        HttpGet request = new HttpGet("http://localhost:" + wireMockServer.port() + "/abonne/1");
        request.addHeader("accept", "application/json");
        HttpResponse httpResponse = httpClient.execute(request);
        String jsonResponse = EntityUtils.toString(httpResponse.getEntity());

        ObjectMapper objectMapper = new ObjectMapper();
        abonne = objectMapper.readValue(jsonResponse, Abonne.class);

        assertThat(abonne.getId(), is(1L));
        assertThat(abonne.getAdressePrincipale().getPays(), is("France"));
        assertThat(abonne.getContrats().size(), is(2));

        adressePrincipale = abonne.getAdressePrincipale();
        adressePrincipale.setActive(AdresseHelper.isAdresseActive(active));
        adressePrincipale.setPays(pays);
        abonne.setAdressePrincipale(adressePrincipale);

        wireMockServer.stop();
    }

    @Lorsque("^le conseiller connecté à (.*) modifie l'adresse de l'abonné (.*)$")
    public void le_conseiller_connecté_à_canal_modifie_l_adresse_de_l_abonné_avec_condition(String canal, String condition) throws IOException {
        operation = new Operation();
        operation.setCanalConnexion(canal);
        operation.setNom("modification_adresse_abonne");
        operation.setAbonneConcerne(abonne);
        operation.setSansDateEffet(AdresseHelper.sansDateEffet(condition));

        // simuler l'appel PUT de l'API /abonne/1
        wireMockServer.start();

        configureFor("localhost", wireMockServer.port());
        stubFor(put(urlEqualTo("/abonne/1"))
                .withHeader("content-type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": 1"))
                .willReturn(aResponse().withStatus(200)));

        HttpPut request = new HttpPut("http://localhost:" + wireMockServer.port() + "/abonne/1");
        StringEntity entity = new StringEntity(abonneJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), is(200));
        verify(putRequestedFor(urlEqualTo("/abonne/1"))
                .withHeader("content-type", equalTo("application/json")));

        wireMockServer.stop();

        nombreModification++;
    }

    @Alors("^l'adresse de l'abonné modifiée est enregistrée sur l'ensemble des contrats de l'abonné$")
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

    @Alors("^un mouvement de modification d'adresse est créé$")
    public void un_mouvement_de_modification_d_adresse_est_créé() {
        assertTrue(nombreModification > 0);
    }

}
