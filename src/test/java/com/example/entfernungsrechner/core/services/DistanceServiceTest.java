package com.example.entfernungsrechner.core.services;

import com.example.entfernungsrechner.core.Distance;
import com.example.entfernungsrechner.core.entities.Station;
import com.example.entfernungsrechner.core.repo_abstractions.IStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;

public class DistanceServiceTest {

    IStationRepository iStationRepository;
    DistanceService distanceService;

    /**
     * Das gemockte Repository kennt drei Bahnhöfe.
     */
    @BeforeEach
    void setup() {
        iStationRepository = Mockito.mock(IStationRepository.class);
        distanceService = new DistanceService(iStationRepository);

        Mockito.when(iStationRepository.getStationByDs100(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(iStationRepository.getStationByDs100("FF")).thenReturn(
                Optional.of(Station.builder().ds100("FF").evaNr(8000105L).latitude(50.107145).longitude(8.663789)
                        .name("Frankfurt(Main)Hbf").build())
        );
        Mockito.when(iStationRepository.getStationByDs100("BLS")).thenReturn(Optional.of(
                Station.builder().ds100("BLS").evaNr(8011160L).latitude(52.525592).longitude(13.369545).name("Berlin Hbf").build())
        );
        Mockito.when(iStationRepository.getStationByDs100("KB")).thenReturn(Optional.of(
                Station.builder().ds100("KB").evaNr(8000044L).latitude(50.732008).longitude(7.097136).name("Bonn Hbf").build())
        );

    }

    @Test
    void testeServiceBerechnetEntfernung() {
        Distance distance = distanceService.getDistanceBetween("FF", "BLS");

        assertThat(distance).isEqualTo(
                Distance.builder().from("Frankfurt(Main)Hbf").to("Berlin Hbf").unit("km").distance(423L).build()
        );
    }

    @Test
    void testeServiceBerechnetEntfernung2() {
        Distance distance = distanceService.getDistanceBetween("BLS", "KB");

        assertThat(distance).isEqualTo(
                Distance.builder().from("Berlin Hbf").to("Bonn Hbf").unit("km").distance(477L).build()
        );
    }

    @Test
    void testeServiceCallsRepository() {
        distanceService.getDistanceBetween("FF", "BLS");

        Mockito.verify(iStationRepository, times(1)).getStationByDs100("FF");
        Mockito.verify(iStationRepository, times(1)).getStationByDs100("BLS");
    }

    @Test
    void ungueltigerBahnhof1() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("FF", "unbekannt")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void ungueltigerBahnhof2() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("test", "FF")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void ungueltigerBahnhof3() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("unbekannt", "unbekannt2")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void leererDS100Code() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("FF", "")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);

        assertThatThrownBy(() -> distanceService.getDistanceBetween(null, "FF")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);

        assertThatThrownBy(() -> distanceService.getDistanceBetween("FF", null)).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);

        assertThatThrownBy(() -> distanceService.getDistanceBetween(null, null)).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }


}
