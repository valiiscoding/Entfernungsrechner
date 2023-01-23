package com.example.entfernungsrechner.core;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Haltestelle {
    private Long evaNr;
    private String ds100;
    private String name;
    private Double laenge;
    private Double breite;
}
