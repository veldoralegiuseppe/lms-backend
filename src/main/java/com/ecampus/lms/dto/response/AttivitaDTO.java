package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AttivitaDTO(@NotBlank String descrizione,
                          @NotBlank String tipo,
                          @NotBlank String settimanaProgrammata,
                          ModuloDTO modulo) {
}
