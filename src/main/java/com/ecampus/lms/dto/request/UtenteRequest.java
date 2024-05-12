package com.ecampus.lms.dto.request;

import com.ecampus.lms.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record UtenteRequest(@NotBlank String nome, @NotBlank String cognome, @NotBlank String email, @NotBlank String codiceFiscale,
                            @NotBlank UserRole ruolo) {
}
