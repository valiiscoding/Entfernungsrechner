package com.example.entfernungsrechner.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest {
    /**
     * Ende-zu-Ende Test. H2 wird mit den Daten aus test_data.sql verwendet.
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testeBerechneDistanz() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/BLS")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from': 'Frankfurt(Main)Hbf'," +
                        "'to': 'Berlin Hbf'," +
                        "'distance': 423," +
                        "'unit': 'km'}"));
    }

    @Test
    void testeBerechneDistanz2() throws Exception {
        mockMvc.perform(get("/api/v1/distance/BFBI/KB")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from':'Flughafen BER - Terminal 1-2'," +
                        "'to':'Bonn Hbf'," +
                        "'distance':479," +
                        "'unit':'km'}"));
    }

    @Test
    void testeBerechneDistanzGleicheStadt() throws Exception {
        mockMvc.perform(get("/api/v1/distance/KB/KB")).andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "'from':'Bonn Hbf'," +
                        "'to':'Bonn Hbf'," +
                        "'distance':0," +
                        "'unit':'km'}"));
    }


    @Test
    void testeExceptionMessageIsReturned() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/distance/FF/KLJHB")).andReturn();

        assertThat(mvcResult.getResponse().getErrorMessage()).containsIgnoringCase("'KLJHB' unbekannt");
    }

    @Test
    void testeBerechneDistanzUnbekannterBahnhof() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/KLJHB"))
                .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void testeBerechneDistanzUnbekannterBahnhof2() throws Exception {
        mockMvc.perform(get("/api/v1/distance/unbekannt/BLS"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testeBerechneDistanzUnbekannterBahnhof3() throws Exception {
        mockMvc.perform(get("/api/v1/distance/unbekannt/unbekannt"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testeUnbekannteRoute() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF"))
                .andExpect(status().isNotFound());
    }
}
