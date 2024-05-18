package com.ecampus.lms.dto.response;

import jakarta.validation.constraints.NotBlank;

public record UtenteDTO(@NotBlank String nome,
                        @NotBlank String cognome,
                        @NotBlank String codiceFiscale,
                        @NotBlank String email,
                        @NotBlank String ruolo) {
}
