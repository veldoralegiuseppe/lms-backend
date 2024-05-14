package com.ecampus.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"DATTV_ATTIVITA\"")
public class AttivitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATTV_PK_ID_GENERATOR")
    @SequenceGenerator(name = "DATTV_PK_ID_GENERATOR", sequenceName = "DATTV_PK_ID_GENERATOR", allocationSize = 1)
    @Column(name = "\"DATTV_PK_ID\"", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "\"DATTV_FK_DMODL\"", nullable = false)
    private Integer fkModulo;

    @NotNull
    @Column(name = "\"DATTV_NOME\"", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "\"DATTV_DESCRIZIONE\"", length = Integer.MAX_VALUE)
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DATTV_FK_DMODL")
    private ModuloEntity modulo;

}