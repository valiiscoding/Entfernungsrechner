package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.entities.Station;
import com.example.entfernungsrechner.core.repo_abstractions.IStationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StationRepositoryTest {

    @Autowired
    IStationRepository iStationRepository;

    @Test
    void testAttributeAssignment() {
        Optional<Station> ff = iStationRepository.getStationByDs100("FF");

        assertThat(ff).isPresent();
        assertThat(ff.get()).isEqualTo(Station.builder()
                .evaNr(8000105L)
                .operatorName("DB Station und Service AG")
                .operatorNr(1866L)
                .latitude(50.107145)
                .longitude(8.663789)
                .ds100("FF")
                .ifOpt("de:06412:10")
                .name("Frankfurt(Main)Hbf")
                .status(null)
                .traffic("FV")
                .build());
    }


}
