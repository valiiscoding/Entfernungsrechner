package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.entities.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StationCrudRepository extends CrudRepository<Station, Long> {
    Optional<Station> findByDs100IgnoreCase(String ds100);
}
