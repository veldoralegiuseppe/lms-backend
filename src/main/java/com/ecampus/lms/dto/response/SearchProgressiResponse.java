package com.ecampus.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record SearchProgressiResponse(@NotBlank String nome,
                                      @NotBlank String cognome,
                                      @NotBlank String codiceFiscale,
                                      @NotBlank String nomeCorso,
                                      @NotBlank String esito,
                                      @NotBlank String tipoSessione,
                                      @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataSessione) {
}
