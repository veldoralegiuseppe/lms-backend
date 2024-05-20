package com.ecampus.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"DSECR_SESSIONE\"", schema = "public")
public class SessioneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DSECR_SESSIONE_id_gen")
    @SequenceGenerator(name = "DSECR_SESSIONE_id_gen", sequenceName = "\"DUTNE_PK_ID_GENERATOR\"", allocationSize = 1)
    @Column(name = "\"DSECR_PK_ID\"", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "\"DSECR_DATA\"", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"DSECR_FK_DCORS\"")
    private CorsoEntity corso;

    @NotNull
    @Column(name = "\"DSECR_TIPO\"", nullable = false, length = Integer.MAX_VALUE)
    private String tipo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"DSECR_FK_DDOCU\"")
    private DocumentaleEntity provaScritta;

}