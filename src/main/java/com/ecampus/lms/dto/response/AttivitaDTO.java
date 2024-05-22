package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttivitaDTO(@NotBlank String tipo,
                          @NotBlank Integer settimanaProgrammata,
                          @NotNull Integer idModulo,
                          @NotNull Integer id,
                          @NotNull @NotBlank String idFile) {
}
