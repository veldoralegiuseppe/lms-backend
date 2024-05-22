package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CorsoDetailsDTO(@NotNull Integer id,
                              @NotBlank String nome,
                              @NotBlank String descrizione,
                              List<ModuloDetailsDTO> moduli,
                              List<SessioneDTO> sessioni,
                              List<UtenteDTO> studenti,
                              UtenteDTO docente) {
}
