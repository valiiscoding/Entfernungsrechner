package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.repo_abstractions.IHaltestellenRepository;
import com.example.entfernungsrechner.core.entities.Haltestelle;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HaltestellenRepositoryImpl implements IHaltestellenRepository {

    private final HaltestellenCrudRepository haltestellenRepository;

    public HaltestellenRepositoryImpl(HaltestellenCrudRepository haltestellenRepository) {
        this.haltestellenRepository = haltestellenRepository;
    }

    @Override
    public Optional<Haltestelle> getHaltestelleByDs100(String ds100) {
        return haltestellenRepository.findByDs100IgnoreCase(ds100);
    }
}
