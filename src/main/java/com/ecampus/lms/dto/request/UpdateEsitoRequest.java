package com.ecampus.lms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEsitoRequest(@NotNull Integer idStudente,
                                 @NotNull Integer idSessione,
                                 @NotBlank String esito) {
}
