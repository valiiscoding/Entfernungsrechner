package com.example.entfernungsrechner.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Distance {
    private String from;
    private String to;
    private Long distance;
    private String unit;
}
