package com.example.entfernungsrechner.core;

import com.example.entfernungsrechner.infrastructure.Haltestelle_Entity;
import com.example.entfernungsrechner.infrastructure.HaltestellenDB;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EntfernungsService {

    private final HaltestellenDB haltestellenDB;

    @Autowired
    public EntfernungsService(HaltestellenDB haltestellenDB) {
        this.haltestellenDB = haltestellenDB;
    }

    public Entfernung berechneDistanz(String from, String to) {
        Optional<Haltestelle_Entity> halt1_opt = haltestellenDB.findByDS100(from);
        Optional<Haltestelle_Entity> halt2_opt = haltestellenDB.findByDS100(to);

        //Fehlerüberprüfung
        if (halt1_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Haltestelle DS100='" + from + "' unbekannt oder kein Fernverkehrsbahnhof.");
        }
        if (halt2_opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Haltestelle DS100='" + to + "' unbekannt oder kein Fernverkehrsbahnhof.");
        }

        Haltestelle_Entity halt1 = halt1_opt.get();
        Haltestelle_Entity halt2 = halt2_opt.get();

        Long distance = Distanzrechner.berechneDistanzInKM(halt1.getBreite(), halt1.getLaenge(),
                halt2.getBreite(), halt2.getLaenge());

        return Entfernung.builder()
                .from(halt1.getNAME())
                .to(halt2.getNAME())
                .distance(distance)
                .unit("km")
                .build();
    }
}
