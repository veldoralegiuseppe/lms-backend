package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CorsoDTO(@NotBlank String nome,
                       @NotBlank String descrizione,
                       List<ModuloDTO> moduli,
                       List<SessioneDTO> sessioni,
                       List<UtenteDTO> studenti,
                       UtenteDTO docente) {
}
