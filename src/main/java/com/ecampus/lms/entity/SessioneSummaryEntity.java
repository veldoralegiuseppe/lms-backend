package com.ecampus.lms.entity;

import com.ecampus.lms.entity.key.SessioneSummaryEntityId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "\"VSESM_SESSIONE_SUMMARY\"", schema = "public")
public class SessioneSummaryEntity {
    @EmbeddedId
    private SessioneSummaryEntityId id;

    @Column(name = "\"DUTNE_EMAIL\"", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "\"DUTNE_FK_TTRUT\"", length = Integer.MAX_VALUE)
    private String ruolo;

    @Column(name = "\"DCORS_NOME\"", length = Integer.MAX_VALUE)
    private String nomeCorso;

    @Column(name = "\"DSECR_DATA\"")
    private LocalDate dataSessione;

    @Column(name = "\"DSECR_TIPO\"", length = Integer.MAX_VALUE)
    private String tipoSessione;

    @Column(name = "\"NUMERO_ISCRITTI\"")
    private Integer numeroIscritti;

    @Column(name = "\"PROVE_CONSEGNATE\"")
    private Integer proveConsegnate;

    @Column(name = "\"PROVE_CORRETTE\"")
    private Integer proveCorrette;

}