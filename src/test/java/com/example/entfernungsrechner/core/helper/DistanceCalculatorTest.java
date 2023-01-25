package com.example.entfernungsrechner.core.helper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DistanceCalculatorTest {

    private static final long DISTANCE_FF_TO_BLS_KM = 423;

    @Test
    void testCalcDistanceInKM() {
        //Frankfurt (Main)HBF
        double lat1 = 50.107145;
        double lon1 = 8.663789;

        //Berlin HBF
        double lat2 = 52.525592;
        double lon2 = 13.369545;

        Long distance = DistanceCalculator.calcDistanceInKM(lat1, lon1, lat2, lon2);

        assertThat(distance).isEqualTo(DISTANCE_FF_TO_BLS_KM);
    }

    @Test
    void testCalcDistanceInKMSameStationReturns0() {
        //Frankfurt (Main)HBF
        double lat1 = 50.107145;
        double lon1 = 8.663789;

        Long distance = DistanceCalculator.calcDistanceInKM(lat1, lon1, lat1, lon1);

        assertThat(distance).isEqualTo(0);
    }

}
