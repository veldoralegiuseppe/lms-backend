package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentaleDTO(@NotBlank String id,
                             @NotBlank String nome,
                             @NotBlank String contentType,
                             @NotNull byte[] data,
                             @NotNull String insertDate,
                             @NotNull String updateDate) {
}
