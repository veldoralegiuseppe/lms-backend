package com.ecampus.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"DDOCU_DOCUMENTALE\"", schema = "public")
public class DocumentaleEntity {
    @Id
    @Column(name = "\"DDOCU_PK_ID\"", nullable = false, length = Integer.MAX_VALUE)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    @Column(name = "\"DDOCU_NOME\"", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @NotNull
    @Column(name = "\"DDOCU_TIPO\"", nullable = false, length = Integer.MAX_VALUE)
    private String tipo;

    @NotNull
    @Column(name = "\"DDOCU_DATI\"", nullable = false, columnDefinition="bytea")
    //@Lob
    private byte[] dati;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"DDOCU_FK_DUTNE\"", nullable = false)
    private UtenteEntity utente;

    //@NotNull
    @Column(name = "\"DDOCU_INSERT_DATE\"", nullable = false)
    @CreationTimestamp
    private ZonedDateTime insertDate;

    //@NotNull
    @Column(name = "\"DDOCU_UPDATE_DATE\"", nullable = false)
    @UpdateTimestamp
    private ZonedDateTime updateDate;

    @OneToMany(mappedBy = "provaScritta")
    private Set<IstanzaSessioneEntity> proveEsame = new LinkedHashSet<>();

}