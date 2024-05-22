package com.ecampus.lms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateModuloRequest(@NotBlank String nome, @NotBlank String descrizione, @NotNull Integer idCorso) {
}
