package com.ecampus.lms.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SessioneRequest(@NotBlank String nomeCorso,
                              @NotNull @JsonFormat(shape=JsonFormat.Shape.STRING) LocalDate data,
                              @NotBlank  String tipo) {
}
