package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IstanzaSessioneDTO(@NotNull Integer idSessione,
                                 @NotNull Integer idStudente,
                                 @NotBlank String nomeStudente,
                                 @NotBlank String cognomeStudente,
                                 @NotBlank String codiceFiscale,
                                 @NotBlank String emailStudente,
                                 @NotBlank String idFileStudente,
                                 @NotBlank String nomeFileStudente,
                                 @NotBlank String contentType,
                                 @NotBlank String esito) {
}
