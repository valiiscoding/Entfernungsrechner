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

    public DistanceService(IStationRepository iStationRepository) {
        this.iStationRepository = iStationRepository;
    }

    /**
     * @param from DS100 code of a long-distance train station
     * @param to   DS100 code of long-distance train station
     * @return Distance object containing distance between 'from' and 'to'
     */
    public Distance getDistanceBetween(String from, String to) {
        Optional<Station> halt1_opt = iStationRepository.getStationByDs100(from);
        Optional<Station> halt2_opt = iStationRepository.getStationByDs100(to);

        //Fehlerüberprüfung
        if (halt1_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Station DS100='" + from + "' unknown or no long-distance train station.");
        }
        if (halt2_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Station DS100='" + to + "' unknown or no long-distance train station.");
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
