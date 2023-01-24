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
public class Haltestelle {
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
    private String verkehr;

    @Column(name = "Laenge")
    private Double laenge;

    @Column(name = "Breite")
    private Double breite;

    @Column(name = "Betreiber_Name")
    private String betreiberName;

    @Column(name = "Betreiber_Nr")
    private Long betreiberNr;

    @Column(name = "Status")
    private String status;

}
