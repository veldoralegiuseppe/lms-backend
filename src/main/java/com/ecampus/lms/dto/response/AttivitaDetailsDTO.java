package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttivitaDetailsDTO(@NotNull Integer id,
                                 @NotBlank String tipo,
                                 @NotBlank Integer settimanaProgrammata,
                                 @NotNull Integer idModulo,
                                 @NotNull DocumentaleDTO file) {
}
