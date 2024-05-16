package com.ecampus.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "\"VCRSM_CORSO_SUMMARY\"")
public class CorsoSummaryEntity {
    @Id
    @Column(name = "\"NOME_CORSO\"", length = Integer.MAX_VALUE)
    private String nomeCorso;

    @Column(name = "\"NUMERO_SESSIONI\"")
    private Integer numeroSessioni;

    @Column(name = "\"NUMERO_STUDENTI\"")
    private Integer numeroStudenti;

    @Column(name = "\"NUMERO_MODULI\"")
    private Integer numeroModuli;

}