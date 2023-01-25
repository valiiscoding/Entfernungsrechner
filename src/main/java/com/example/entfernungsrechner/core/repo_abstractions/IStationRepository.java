package com.example.entfernungsrechner.core.repo_abstractions;

import com.example.entfernungsrechner.core.entities.Station;

import java.util.Optional;

public interface IStationRepository {
    /**
     * @param ds100 DS100 code of long-distance train station
     * @return Station object where DS100='ds100' (ignoring case).
     * if not available:the Optional remains empty
     */
    Optional<Station> getStationByDs100(String ds100);
}
