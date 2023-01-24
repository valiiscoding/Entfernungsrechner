package com.example.entfernungsrechner.core.services;

import com.example.entfernungsrechner.core.Entfernung;
import com.example.entfernungsrechner.core.entities.Haltestelle;
import com.example.entfernungsrechner.core.repo_abstractions.IHaltestellenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;


public class EntfernungsServiceTest {

    IHaltestellenRepository iHaltestellenRepository;
    EntfernungsService entfernungsService;

    /**
     * Das gemockte Repository kennt drei Bahnhöfe.
     */
    @BeforeEach
    void setup() {
        iHaltestellenRepository = Mockito.mock(IHaltestellenRepository.class);
        entfernungsService = new EntfernungsService(iHaltestellenRepository);

        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100("FF")).thenReturn(
                Optional.of(Haltestelle.builder().ds100("FF").evaNr(8000105L).breite(50.107145).laenge(8.663789)
                        .name("Frankfurt(Main)Hbf").build())
        );
        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100("BLS")).thenReturn(Optional.of(
                Haltestelle.builder().ds100("BLS").evaNr(8011160L).breite(52.525592).laenge(13.369545).name("Berlin Hbf").build())
        );
        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100("KB")).thenReturn(Optional.of(
                Haltestelle.builder().ds100("KB").evaNr(8000044L).breite(50.732008).laenge(7.097136).name("Bonn Hbf").build())
        );

    }

    @Test
    void testeServiceBerechnetEntfernung() {
        Entfernung entfernung = entfernungsService.berechneDistanz("FF", "BLS");

        assertThat(entfernung).isEqualTo(
                Entfernung.builder().from("Frankfurt(Main)Hbf").to("Berlin Hbf").unit("km").distance(423L).build()
        );
    }

    @Test
    void testeServiceBerechnetEntfernung2() {
        Entfernung entfernung = entfernungsService.berechneDistanz("BLS", "KB");

        assertThat(entfernung).isEqualTo(
                Entfernung.builder().from("Berlin Hbf").to("Bonn Hbf").unit("km").distance(477L).build()
        );
    }

    @Test
    void testeServiceCallsRepository() {
        entfernungsService.berechneDistanz("FF", "BLS");

        Mockito.verify(iHaltestellenRepository, times(1)).getHaltestelleByDs100("FF");
        Mockito.verify(iHaltestellenRepository, times(1)).getHaltestelleByDs100("BLS");
    }

    @Test
    void ungueltigerBahnhof1() {
        assertThatThrownBy(() -> entfernungsService.berechneDistanz("FF", "unbekannt")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void ungueltigerBahnhof2() {
        assertThatThrownBy(() -> entfernungsService.berechneDistanz("test", "FF")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void ungueltigerBahnhof3() {
        assertThatThrownBy(() -> entfernungsService.berechneDistanz("unbekannt", "unbekannt2")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void leererDS100Code() {
        assertThatThrownBy(() -> entfernungsService.berechneDistanz("FF", "")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);

        assertThatThrownBy(() -> entfernungsService.berechneDistanz(null, "FF")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);

        assertThatThrownBy(() -> entfernungsService.berechneDistanz("FF", null)).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);

        assertThatThrownBy(() -> entfernungsService.berechneDistanz(null, null)).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }


}
