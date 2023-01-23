package com.example.entfernungsrechner.core.services;

import com.example.entfernungsrechner.core.Entfernung;
import com.example.entfernungsrechner.core.Haltestelle;
import com.example.entfernungsrechner.core.helper.Distanzrechner;
import com.example.entfernungsrechner.core.IHaltestelleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EntfernungsService {

    private final IHaltestelleRepository haltestelleRepository;

    public EntfernungsService(IHaltestelleRepository haltestelleRepository) {
        this.haltestelleRepository = haltestelleRepository;
    }

    public Entfernung berechneDistanz(String from, String to) {
        Optional<Haltestelle> halt1_opt = haltestelleRepository.getHaltestelleByDS100(from);
        Optional<Haltestelle> halt2_opt = haltestelleRepository.getHaltestelleByDS100(to);

        //Fehlerüberprüfung
        if (halt1_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Haltestelle DS100='" + from + "' unbekannt oder kein Fernverkehrsbahnhof.");
        }
        if (halt2_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Haltestelle DS100='" + to + "' unbekannt oder kein Fernverkehrsbahnhof.");
        }

        Haltestelle halt1 = halt1_opt.get();
        Haltestelle halt2 = halt2_opt.get();

        Long distance = Distanzrechner.berechneDistanzInKM(halt1.getBreite(), halt1.getLaenge(),
                halt2.getBreite(), halt2.getLaenge());

        return Entfernung.builder()
                .from(halt1.getName())
                .to(halt2.getName())
                .distance(distance)
                .unit("km")
                .build();
    }
}