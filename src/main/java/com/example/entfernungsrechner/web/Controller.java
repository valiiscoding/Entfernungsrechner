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

    /**
     * @param fromDS100 Path variable (should contain DS100 code of long-distance train station)
     * @param toDS100   Path variable (should contain DS100 code of long-distance train station)
     * @return Distance object as JSON, if valid DS100 codes
     * if one of the path variables are not valid DS100 codes, exceptions throw
     */
    @GetMapping(value = "/distance/{fromDS100}/{toDS100}")
    public ResponseEntity<Distance> entfernung(@PathVariable String fromDS100, @PathVariable String toDS100) {
        Distance distance = distanceService.getDistanceBetween(fromDS100, toDS100);
        return new ResponseEntity<>(distance, HttpStatus.OK);
    }
}
