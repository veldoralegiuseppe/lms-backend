package com.ecampus.lms.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record SearchProgressiRequest(@NotBlank String nomeCorso,
                                     @NotBlank String nome,
                                     @NotBlank String cognome,
                                     @NotBlank String codiceFiscale,
                                     @NotBlank String tipoSessione,
                                     LocalDate dataDa,
                                     LocalDate dataA) {
}
