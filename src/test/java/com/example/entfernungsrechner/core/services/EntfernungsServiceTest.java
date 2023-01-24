package com.example.entfernungsrechner.core.services;

import com.example.entfernungsrechner.core.Entfernung;
import com.example.entfernungsrechner.core.entities.Haltestelle;
import com.example.entfernungsrechner.core.repo_abstractions.IHaltestellenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;


public class EntfernungsServiceTest {

    @Mock
    IHaltestellenRepository iHaltestellenRepository;
    EntfernungsService entfernungsService;

    @BeforeEach
    void setup() {
        iHaltestellenRepository = Mockito.mock(IHaltestellenRepository.class);
        entfernungsService = new EntfernungsService(iHaltestellenRepository);

        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100("FF")).thenReturn(
                Optional.of(Haltestelle.builder()
                        .ds100("FF")
                        .evaNr(8000105L)
                        .breite(50.107145)
                        .laenge(8.663789)
                        .name("Frankfurt(Main)Hbf")
                        .build())
        );

        Mockito.when(iHaltestellenRepository.getHaltestelleByDs100("BLS")).thenReturn(
                Optional.of(Haltestelle.builder()
                        .ds100("BLS")
                        .evaNr(8011160L)
                        .breite(52.525592)
                        .laenge(13.369545)
                        .name("Berlin Hbf")
                        .build())
        );

    }

    @Test
    void testeServiceBerechnetEntfernung() {
        Entfernung entfernung = entfernungsService.berechneDistanz("FF", "BLS");

        Mockito.verify(iHaltestellenRepository, times(1)).getHaltestelleByDs100("FF");
        Mockito.verify(iHaltestellenRepository, times(1)).getHaltestelleByDs100("BLS");

        assertThat(entfernung).isEqualTo(
                Entfernung.builder().from("Frankfurt(Main)Hbf").to("Berlin Hbf").unit("km").distance(423L).build()
        );
    }


}
