package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.Haltestelle;
import com.example.entfernungsrechner.core.IHaltestelleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HaltestellenRepositoryImpl implements IHaltestelleRepository {

    private final HaltestellenCrudRepository haltestellenRepository;

    public HaltestellenRepositoryImpl(HaltestellenCrudRepository haltestellenRepository) {
        this.haltestellenRepository = haltestellenRepository;
    }

    @Override
    public Optional<Haltestelle> getHaltestelleByDS100(String ds100) {
        Optional<Haltestelle_Entity> haltestelle = haltestellenRepository.findByDS100IgnoreCase(ds100);
        if (haltestelle.isEmpty())
            return Optional.empty();

        Haltestelle build = Haltestelle.builder()
                .breite(haltestelle.get().getBreite())
                .laenge(haltestelle.get().getLaenge())
                .ds100(haltestelle.get().getDS100())
                .evaNr(haltestelle.get().getEVA_NR())
                .name(haltestelle.get().getNAME())
                .build();

        return Optional.of(build);
    }
}
