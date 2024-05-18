package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

public record MessageDTO(@NotBlank String message) {
}
