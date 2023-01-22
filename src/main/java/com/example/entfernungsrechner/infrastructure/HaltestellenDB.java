package com.example.entfernungsrechner.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HaltestellenDB extends CrudRepository<Haltestelle_Entity, Long> {
    Optional<Haltestelle_Entity> findByDS100(String ds100);
}
