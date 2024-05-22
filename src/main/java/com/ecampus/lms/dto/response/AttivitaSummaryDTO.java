package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttivitaSummaryDTO(@NotBlank String nomeCorso,
                                 @NotBlank String dettaglio,
                                 @NotNull Integer settimanaProgrammata) {
}
