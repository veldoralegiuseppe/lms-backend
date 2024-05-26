package com.ecampus.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SessioneDetailsResponse(@NotNull Integer idCorso,
                                      @NotNull Integer idDocente,
                                      @NotBlank String nomeDocente,
                                      @NotBlank String cognomeDocente,
                                      @NotBlank String emailDocente,
                                      @NotBlank String nomeCorso,
                                      @NotNull Integer idSessione,
                                      @NotBlank String tipoSessione,
                                      @NotNull @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataSessione,
                                      @NotNull Integer numeroIscritti,
                                      @NotBlank String idProvaSomministrata,
                                      @NotBlank String nomeProvaSomministrata,
                                      @NotBlank String contentType,
                                      List<IstanzaSessioneDTO> esami) {
}
