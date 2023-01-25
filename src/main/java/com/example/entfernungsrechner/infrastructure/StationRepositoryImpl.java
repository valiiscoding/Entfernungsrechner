package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.repo_abstractions.IStationRepository;
import com.example.entfernungsrechner.core.entities.Station;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StationRepositoryImpl implements IStationRepository {

    private final StationCrudRepository stationCrudRepository;

    public StationRepositoryImpl(StationCrudRepository stationCrudRepository) {
        this.stationCrudRepository = stationCrudRepository;
    }

    @Override
    public Optional<Station> getStationByDs100(String ds100) {
        return stationCrudRepository.findByDs100IgnoreCase(ds100);
    }
}
