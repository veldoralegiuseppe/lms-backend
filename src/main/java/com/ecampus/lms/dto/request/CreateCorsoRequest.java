package com.ecampus.lms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCorsoRequest(@NotBlank String nome, @NotBlank String descrizione, String emailDocente) {
}
