package com.example.entfernungsrechner.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "DS100")
    private List<String> ds100;

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
