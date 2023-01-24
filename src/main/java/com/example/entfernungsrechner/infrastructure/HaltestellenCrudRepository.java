package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.entities.Haltestelle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HaltestellenCrudRepository extends CrudRepository<Haltestelle, Long> {
    Optional<Haltestelle> findByDs100IgnoreCase(String ds100);
}
