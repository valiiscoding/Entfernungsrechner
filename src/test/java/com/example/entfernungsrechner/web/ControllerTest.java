package com.example.entfernungsrechner.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest {
    /**
     * Integrationstest. H2 wird mit den Daten aus test_data.sql gestartet.
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDistanceDifferentStations() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/BLS")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from': 'Frankfurt(Main)Hbf'," +
                        "'to': 'Berlin Hbf'," +
                        "'distance': 423," +
                        "'unit': 'km'}"));
    }

    @Test
    void testGetDistanceDifferentStationsMixedCaseAndNotTrimmed() throws Exception {
        mockMvc.perform(get("/api/v1/distance/ Ff / blS ")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from': 'Frankfurt(Main)Hbf'," +
                        "'to': 'Berlin Hbf'," +
                        "'distance': 423," +
                        "'unit': 'km'}"));
    }

    @Test
    void testGetDistanceDifferentStations2() throws Exception {
        mockMvc.perform(get("/api/v1/distance/BFBI/KB")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from':'Flughafen BER - Terminal 1-2'," +
                        "'to':'Bonn Hbf'," +
                        "'distance':479," +
                        "'unit':'km'}"));
    }

    @Test
    void testGetDistanceDifferentStationsMixedCaseUntrimmed() throws Exception {
        mockMvc.perform(get("/api/v1/distance/ Bfbi /   kB ")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from':'Flughafen BER - Terminal 1-2'," +
                        "'to':'Bonn Hbf'," +
                        "'distance':479," +
                        "'unit':'km'}"));
    }


    @Test
    void testGetDistanceSameStationLowerCase() throws Exception {
        mockMvc.perform(get("/api/v1/distance/kb/KB")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from':'Bonn Hbf'," +
                        "'to':'Bonn Hbf'," +
                        "'distance':0," +
                        "'unit':'km'}"));
    }


    @Test
    void testExceptionMessageIsReturned() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/distance/FF/KLJHB")).andReturn();

        assertThat(mvcResult.getResponse().getErrorMessage()).containsIgnoringCase("'KLJHB' unknown");
    }

    @Test
    void testExceptionMessageIsReturned2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/distance/KLJHB/FF")).andReturn();

        assertThat(mvcResult.getResponse().getErrorMessage()).containsIgnoringCase("'KLJHB' unknown");
    }

    @Test
    void testUnknownStationReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/KLQ"))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void testUnknownStationReturnsBadRequest2() throws Exception {
        mockMvc.perform(get("/api/v1/distance/KLQ/BLS"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUnknownStationReturnsBadRequest3() throws Exception {
        mockMvc.perform(get("/api/v1/distance/KLQ/KLQ"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testIllegalRouteReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNumberAsPathVarNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF1/FF"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNumberAsPathVarNotFound2() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/FF1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testMoreThan6LettersAsPathVarNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/FFHDSKK"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testMoreThan6LettersAsPathVarNotFound2() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FFIUGOK/FF"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testLessThanTwoLettersAsPathVar() throws Exception {
        mockMvc.perform(get("/api/v1/distance/BLS/F"))
                .andExpect(status().isNotFound());
    }
}
