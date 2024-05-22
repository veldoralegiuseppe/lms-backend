package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ModuloDetailsDTO(@NotNull Integer id,
                               @NotBlank String nome,
                               @NotBlank String descrizione,
                               @NotNull Integer idCorso,
                               @NotBlank String nomeCorso,
                               List<AttivitaDTO> attivita) {
}
