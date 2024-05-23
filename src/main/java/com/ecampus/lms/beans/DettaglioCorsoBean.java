package com.ecampus.lms.beans;

import com.ecampus.lms.enums.TipoAttivita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;


@AllArgsConstructor
@Getter
@Setter
public class DettaglioCorsoBean implements Serializable {
    Integer idCorso;
    Integer idModulo;
    Integer idAttivita;
    String nomeCorso;
    String descrizioneCorso;
    String nomeModulo;
    String descrizioneModulo;
    TipoAttivita tipoAttivita;
    Integer settimanaProgrammata;
    String idFile;
    String nomeFile;
    String contentType;
    ZonedDateTime fileInsertDate;
    ZonedDateTime fileUpdateDate;
}