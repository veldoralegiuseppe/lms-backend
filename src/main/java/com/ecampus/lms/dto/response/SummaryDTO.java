package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SummaryDTO(@NotBlank String nomeCorso, Integer moduli, Integer sessioni, Integer studenti) {
}
