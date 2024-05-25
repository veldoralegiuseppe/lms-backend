package com.ecampus.lms.entity;

import com.ecampus.lms.entity.key.IstanzaSessioneEntityId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"RSEST_SESSIONE_STUDENTE\"", schema = "public")
public class IstanzaSessioneEntity {
    @EmbeddedId
    private IstanzaSessioneEntityId id;

    @MapsId("idSessione")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"RSEST_FK_DSECR\"", nullable = false)
    private SessioneEntity sessione;

    @MapsId("idStudente")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"RSEST_FK_DUTNE\"", nullable = false)
    private UtenteEntity studente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"RSEST_FK_DDOC\"")
    private DocumentaleEntity provaScritta;

    @Column(name = "\"RSEST_ESITO\"")
    private Integer esito;

}