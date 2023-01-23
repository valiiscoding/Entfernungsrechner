package com.example.entfernungsrechner.web;

import com.example.entfernungsrechner.core.Entfernung;
import com.example.entfernungsrechner.core.services.EntfernungsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    private final EntfernungsService entfernungsService;

    public Controller(EntfernungsService entfernungsService) {
        this.entfernungsService = entfernungsService;
    }

    @GetMapping(value = "/distance/{from}/{to}")
    public ResponseEntity<Entfernung> entfernung(@PathVariable String from, @PathVariable String to) {
        Entfernung entfernung = entfernungsService.berechneDistanz(from, to);
        return new ResponseEntity<>(entfernung, HttpStatus.OK);
    }
}
