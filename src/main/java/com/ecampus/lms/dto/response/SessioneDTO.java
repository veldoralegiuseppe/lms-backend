package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

public record SessioneDTO(Integer id, @NotBlank String nomeCorso, @NotBlank String data, @NotBlank  String tipo, DocumentaleDTO provaScritta) {
}
