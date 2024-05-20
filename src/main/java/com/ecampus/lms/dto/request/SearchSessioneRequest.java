package com.ecampus.lms.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record SearchSessioneRequest(@NotBlank String nomeCorso,
                                    LocalDate dataDa,
                                    LocalDate dataA,
                                    @NotBlank String tipo) {
}
