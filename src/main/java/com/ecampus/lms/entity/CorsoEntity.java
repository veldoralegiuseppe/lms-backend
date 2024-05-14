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
@Table(name = "\"DCORS_CORSO\"", schema = "public")
public class CorsoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DCORS_PK_ID_GENERATOR")
    @SequenceGenerator(name = "DCORS_PK_ID_GENERATOR", sequenceName = "\"DCORS_PK_ID_GENERATOR\"", allocationSize = 1)
    @Column(name = "\"DCORS_PK_ID\"", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "\"DCORS_NOME\"", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "\"DCORS_DESCRIZIONE\"", length = Integer.MAX_VALUE)
    private String descrizione;

    @Column(name = "\"DCORS_FK_DUTNE\"")
    private Integer fkProfessore;

    @OneToMany(mappedBy = "corso")
    private Set<ModuloEntity> moduli = new LinkedHashSet<>();

    @OneToMany(mappedBy = "corso")
    private Set<SessioneEntity> sessioni = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "\"RCRST_CORSO_STUDENTE\"",
            joinColumns = @JoinColumn(name = "\"RCRST_FK_DCORS\""),
            inverseJoinColumns = @JoinColumn(name = "\"RCRST_FK_DUTNE\""))
    private Set<UtenteEntity> studenti = new LinkedHashSet<>();

}