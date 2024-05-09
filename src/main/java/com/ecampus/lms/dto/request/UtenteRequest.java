package com.ecampus.lms.dto.request;

import com.ecampus.lms.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UtenteRequest(@NotBlank String nome, @NotBlank String cognome, @NotBlank String email, @NotBlank String codiceFiscale,
                            @NotBlank UserRole ruolo) {
}
