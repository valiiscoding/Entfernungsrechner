package com.example.entfernungsrechner.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HaltestellenCrudRepository extends CrudRepository<Haltestelle_Entity, Long> {
    Optional<Haltestelle_Entity> findByDS100IgnoreCase(String ds100);
}
