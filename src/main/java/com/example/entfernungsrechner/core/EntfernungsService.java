package com.example.entfernungsrechner;

import com.example.entfernungsrechner.core.Distanzrechner;
import com.example.entfernungsrechner.core.Entfernung;
import com.example.entfernungsrechner.infrastructure.Haltestelle;
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
        Optional<Haltestelle> halt1_opt = haltestellenDB.findByDS100(from);
        Optional<Haltestelle> halt2_opt = haltestellenDB.findByDS100(to);

        //Fehlerüberprüfung
        if (halt1_opt.isPresent() && halt2_opt.isPresent()) {
            Haltestelle halt1 = halt1_opt.get();
            Haltestelle halt2 = halt2_opt.get();

            Long distance = Distanzrechner.berechneDistanzInKM(halt1.getBreite(), halt1.getLaenge(),
                    halt2.getBreite(), halt2.getLaenge());

            return Entfernung.builder()
                    .from(halt1.getNAME())
                    .to(halt2.getNAME())
                    .distance(distance)
                    .unit("km")
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ungültige Haltestelle(n)");
    }
}
