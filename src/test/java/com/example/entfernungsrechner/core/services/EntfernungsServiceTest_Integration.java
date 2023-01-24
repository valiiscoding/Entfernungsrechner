package com.example.entfernungsrechner.core.services;

import com.example.entfernungsrechner.core.Entfernung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class EntfernungsServiceTest_Integration {

    @Autowired
    EntfernungsService entfernungsService;

    @Test
    void setup(){

    }


    @Test
    void testeServiceBerechnetEntfernung() {
        Entfernung entfernung = entfernungsService.berechneDistanz("FF", "BLS");

        assertThat(entfernung).isEqualTo(
                Entfernung.builder().from("Frankfurt(Main)Hbf").to("Berlin Hbf").unit("km").distance(423L).build()
        );
    }

    @Test
    void ungueltigerBahnhof() {
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
