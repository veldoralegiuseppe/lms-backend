package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

public record SummaryDTO(@NotBlank String nomeCorso, Integer moduli, Integer sessioni, Integer studenti) {
}
