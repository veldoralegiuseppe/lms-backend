package com.ecampus.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"DMODL_MODULO\"", schema = "public")
public class ModuloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMODL_PK_ID_GENERATOR")
    @SequenceGenerator(name = "DMODL_PK_ID_GENERATOR", sequenceName = "\"DMODL_PK_ID_GENERATOR\"", allocationSize = 1)
    @Column(name = "\"DMODL_PK_ID\"", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "\"DMODL_NOME\"", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "\"DMODL_DESCRIZIONE\"", length = Integer.MAX_VALUE)
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"DMODL_FK_DCORS\"")
    private CorsoEntity corso;

    @OneToMany(mappedBy = "modulo")
    private Set<AttivitaEntity> attivita = new LinkedHashSet<>();

}