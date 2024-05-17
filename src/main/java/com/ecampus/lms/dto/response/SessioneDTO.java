package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.time.ZonedDateTime;

public record SessioneDTO(@NotBlank String nomeCorso, @NotBlank String dataOra, @NotBlank  String tipo) {
}
