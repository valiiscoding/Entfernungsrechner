package com.example.entfernungsrechner.web;

import com.example.entfernungsrechner.core.Distance;
import com.example.entfernungsrechner.core.services.DistanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    private final DistanceService distanceService;

    public Controller(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping(value = "/distance/{from}/{to}")
    public ResponseEntity<Distance> entfernung(@PathVariable String from, @PathVariable String to) {
        Distance distance = distanceService.getDistanceBetween(from, to);
        return new ResponseEntity<>(distance, HttpStatus.OK);
    }
}
