package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.entities.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StationCrudRepository extends CrudRepository<Station, Long> {

    /**
     * @param ds100 DS100 code (in upper case and trimmed)
     * @return station, which has ds100 code
     */
    Optional<Station> findByDs100Contains(String ds100);
}
