package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CorsoSummaryDTO(@NotNull Integer id, @NotBlank String nomeCorso, Integer moduli, Integer sessioni, Integer studenti) {
}
