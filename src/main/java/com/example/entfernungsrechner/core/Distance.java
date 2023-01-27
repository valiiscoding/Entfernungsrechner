package com.example.entfernungsrechner.core;

import lombok.Builder;

@Builder
public record Distance(String from, String to, Long distance, String unit) {
}
