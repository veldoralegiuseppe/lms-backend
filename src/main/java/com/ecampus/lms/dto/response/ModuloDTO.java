package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ModuloDTO(@NotBlank String nome,
                        @NotBlank String descrizione,
                        CorsoDTO corso,
                        List<AttivitaDTO> attivita) {
}
