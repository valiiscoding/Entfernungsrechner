package com.example.entfernungsrechner.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "haltestelle")
public class Station {
    @Id
    @Column(name = "EVA_NR")
    private Long evaNr;

    @Column(name = "DS100")
    private String ds100;

    @Column(name = "IFOPT")
    private String ifOpt;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VERKEHR")
    private String traffic;

    @Column(name = "Laenge")
    private Double longitude;

    @Column(name = "Breite")
    private Double latitude;

    @Column(name = "Betreiber_Name")
    private String operatorName;

    @Column(name = "Betreiber_Nr")
    private Long operatorNr;

    @Column(name = "Status")
    private String status;

}
