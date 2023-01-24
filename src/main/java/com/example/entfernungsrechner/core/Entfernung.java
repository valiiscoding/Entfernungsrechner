package com.example.entfernungsrechner.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Entfernung {
    private String from;
    private String to;
    private Long distance;
    private String unit;
}
