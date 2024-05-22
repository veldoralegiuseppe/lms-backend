package com.ecampus.lms.entity;

import com.ecampus.lms.enums.TipoAttivita;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"DATTV_ATTIVITA\"", schema = "public")
public class AttivitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATTV_PK_ID_GENERATOR")
    @SequenceGenerator(name = "DATTV_PK_ID_GENERATOR", sequenceName = "\"DATTV_PK_ID_GENERATOR\"", allocationSize = 1)
    @Column(name = "\"DATTV_PK_ID\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"DATTV_FK_DMODL\"")
    private ModuloEntity modulo;

    @NotNull
    @Column(name = "\"DATTV_TIPO\"", nullable = false, length = Integer.MAX_VALUE)
    @Enumerated(EnumType.STRING)
    private TipoAttivita tipo;

    @NotNull
    @Column(name = "\"DATTV_SETTIMANA_PROGRAMMATA\"", nullable = false)
    private Integer settimanaProgrammata;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"DATTV_FK_DDOCU\"", nullable = false)
    private DocumentaleEntity file;

}