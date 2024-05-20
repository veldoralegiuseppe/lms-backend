package com.ecampus.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SessioneDTO(Integer id,
                          @NotBlank String nomeCorso,
                          @NotNull @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate data,
                          @NotBlank  String tipo,
                          DocumentaleDTO provaScritta) {
}
