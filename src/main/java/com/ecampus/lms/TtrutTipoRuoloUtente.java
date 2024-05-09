package com.ecampus.lms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"TTRUT_TIPO_RUOLO_UTENTE\"")
public class TtrutTipoRuoloUtente {
    @Id
    @Column(name = "\"TTRUT_PK_VALUE\"", nullable = false, length = Integer.MAX_VALUE)
    private String ttrutPkValue;

    @Column(name = "\"TTRUT_PK_DESCRIPTION\"", length = Integer.MAX_VALUE)
    private String ttrutPkDescription;

}