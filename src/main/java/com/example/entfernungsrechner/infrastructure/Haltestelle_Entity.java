package com.example.entfernungsrechner.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "haltestelle")
public class Haltestelle_Entity {
    @Id
    @Column
    private Long EVA_NR;

    @Column
    private String DS100;

    @Column
    private String IFOPT;

    @Column
    private String NAME;

    @Column
    private String VERKEHR;

    @Column
    private Double Laenge;

    @Column
    private Double Breite;

    @Column
    private String Betreiber_Name;

    @Column
    private Long Betreiber_Nr;

    @Column
    private String Status;

}
