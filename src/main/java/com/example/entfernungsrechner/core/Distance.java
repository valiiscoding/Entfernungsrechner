package com.example.entfernungsrechner.core;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Distance {
    String from;
    String to;
    Long distance;
    String unit;
}
