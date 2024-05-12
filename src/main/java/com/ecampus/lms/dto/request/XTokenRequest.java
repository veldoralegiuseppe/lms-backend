package com.ecampus.lms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record XTokenRequest(@NotBlank String xToken) {
}
