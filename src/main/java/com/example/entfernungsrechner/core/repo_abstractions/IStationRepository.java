package com.example.entfernungsrechner.core.repo_abstractions;

import com.example.entfernungsrechner.core.entities.Station;

import java.util.Optional;

public interface IStationRepository {
    Optional<Station> getStationByDs100(String ds100);
}
