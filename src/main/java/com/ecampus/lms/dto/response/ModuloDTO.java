package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ModuloDTO(@NotNull Integer id,
                        @NotBlank String nome,
                        @NotBlank String descrizione,
                        @NotNull Integer idCorso,
                        @NotBlank String nomeCorso,
                        List<Integer> idsAttivita) {
}
