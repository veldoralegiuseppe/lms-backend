package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CorsoDTO(Integer id,
                       @NotBlank String nome,
                       @NotBlank String descrizione,
                       List<Integer> idsModuli,
                       List<Integer> idsSessioni,
                       List<Integer> idsStudenti,
                       Integer idDocente) {
}
