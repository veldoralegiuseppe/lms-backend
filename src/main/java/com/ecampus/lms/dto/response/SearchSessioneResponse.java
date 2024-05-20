package com.ecampus.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record SearchSessioneResponse(@NotBlank String nomeCorso,
                                     @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate data,
                                     @NotBlank String tipo,
                                     @NotBlank String nomeDocente,
                                     @NotBlank String cognomeDocente,
                                     @NotBlank String emailDocente,
                                     Integer numeroStudenti) {
}
