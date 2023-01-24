package com.example.entfernungsrechner.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Berlin Hbf")))
                .andExpect(content().string(containsString("Frankfurt")))
                .andExpect(content().string(containsString("423")));
    }

    @Test
    void testeBerechneDistanzUnbekannterBahnhof() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF/unbekannt"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testeUnbekannteRoute() throws Exception {
        mockMvc.perform(get("/api/v1/distance/FF"))
                .andExpect(status().isNotFound());
    }
}
