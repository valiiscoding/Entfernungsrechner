package com.example.entfernungsrechner.core.services;

import com.example.entfernungsrechner.core.Distance;
import com.example.entfernungsrechner.core.entities.Station;
import com.example.entfernungsrechner.core.helper.DistanceCalculator;
import com.example.entfernungsrechner.core.repo_abstractions.IStationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class DistanceService {

    private final IStationRepository iStationRepository;

    public DistanceService(IStationRepository haltestelleRepository) {
        this.iStationRepository = haltestelleRepository;
    }

    public Distance getDistanceBetween(String from, String to) {
        Optional<Station> halt1_opt = iStationRepository.getStationByDs100(from);
        Optional<Station> halt2_opt = iStationRepository.getStationByDs100(to);

        //Fehlerüberprüfung
        if (halt1_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Haltestelle DS100='" + from + "' unbekannt oder kein Fernverkehrsbahnhof.");
        }
        if (halt2_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Haltestelle DS100='" + to + "' unbekannt oder kein Fernverkehrsbahnhof.");
        }

        Station halt1 = halt1_opt.get();
        Station halt2 = halt2_opt.get();

        Long distance = DistanceCalculator.calcDistanceInKM(halt1.getLatitude(), halt1.getLongitude(),
                halt2.getLatitude(), halt2.getLongitude());

        return Distance.builder()
                .from(halt1.getName())
                .to(halt2.getName())
                .distance(distance)
                .unit("km")
                .build();
    }
}
