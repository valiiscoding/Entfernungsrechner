package com.example.entfernungsrechner.core;

import java.util.Optional;

public interface IHaltestelleRepository {
    Optional<Haltestelle> getHaltestelleByDS100(String ds100);
}
