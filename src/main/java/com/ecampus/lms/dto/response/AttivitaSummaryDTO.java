package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AttivitaSummaryDTO(@NotBlank String nomeCorso,
                                 @NotBlank String dettaglio,
                                 @NotBlank String settimanaProgrammata) {
}
