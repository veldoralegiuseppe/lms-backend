package com.ecampus.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record DocumentaleDTO(@NotBlank String id,
                             @NotBlank String nome,
                             @NotBlank String contentType,
                             @NotNull byte[] data,
                             @NotNull @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") ZonedDateTime insertDate,
                             @NotNull @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") ZonedDateTime updateDate) {
}
