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
     * Add 3 stations to the mocked repository
     */
    @BeforeEach
    void setup() {
        iStationRepository = Mockito.mock(IStationRepository.class);
        distanceService = new DistanceService(iStationRepository);

        Mockito.when(iStationRepository.getStationByDs100(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(iStationRepository.getStationByDs100("FF")).thenReturn(
                Optional.of(Station.builder().evaNr(8000105L).operatorName("DB Station und Service AG").operatorNr(1866L)
                        .latitude(50.107145).longitude(8.663789).ds100("FF").ifOpt("de:06412:10")
                        .name("Frankfurt(Main)Hbf").status(null).traffic("FV").build())
        );
        Mockito.when(iStationRepository.getStationByDs100("BLS")).thenReturn(Optional.of(
                Station.builder().ds100("BLS").evaNr(8011160L).latitude(52.525592).longitude(13.369545).name("Berlin Hbf")
                        .operatorName("DB Station und Service AG\t").operatorNr(1071L).ifOpt("de:11000:900003201")
                        .status(null).traffic("FV").build())
        );
        Mockito.when(iStationRepository.getStationByDs100("KB")).thenReturn(Optional.of(
                Station.builder().ds100("KB").evaNr(8000044L).latitude(50.732008).longitude(7.097136).name("Bonn Hbf")
                        .operatorName("DB Station und Service AG").operatorNr(767L).ifOpt("de:05314:61101")
                        .status(null).traffic("FV").build())
        );

    }

    @Test
    void testGetDistanceBetween() {
        Distance distance = distanceService.getDistanceBetween("FF", "BLS");

        assertThat(distance).isEqualTo(
                Distance.builder().from("Frankfurt(Main)Hbf").to("Berlin Hbf").unit("km").distance(423L).build()
        );
    }

    @Test
    void testGetDistanceBetween2() {
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
    void unknownStation() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("FF", "unbekannt")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void unknownStation2() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("test", "FF")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void unknownStation3() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("unbekannt", "unbekannt2")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void emptyDS100Code() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("FF", "")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void emptyDS100Code2() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween(null, "FF")).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void emptyDS100Code3() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween("FF", null)).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }

    @Test
    void emptyDS100Code4() {
        assertThatThrownBy(() -> distanceService.getDistanceBetween(null, null)).isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode().is4xxClientError()).isEqualTo(true);
    }
}
