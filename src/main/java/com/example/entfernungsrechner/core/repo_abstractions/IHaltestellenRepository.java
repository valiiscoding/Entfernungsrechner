package com.example.entfernungsrechner.core.repo_abstractions;

import com.example.entfernungsrechner.core.entities.Haltestelle;

import java.util.Optional;

public interface IHaltestellenRepository {
    Optional<Haltestelle> getHaltestelleByDs100(String ds100);
}
