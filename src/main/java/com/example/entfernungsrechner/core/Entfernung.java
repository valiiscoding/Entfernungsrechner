package com.example.entfernungsrechner;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Entfernung {
    private String from;
    private String to;
    private Long distance;
    private String unit;
}
