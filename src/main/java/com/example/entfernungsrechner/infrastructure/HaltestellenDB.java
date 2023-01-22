package com.example.entfernungsrechner;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HaltestellenDB extends CrudRepository<Haltestelle, Long> {
    Optional<Haltestelle> findByDS100(String ds100);
}
