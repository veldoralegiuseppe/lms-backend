package com.ecampus.lms.dto.request;

import com.ecampus.lms.dto.response.UtenteDTO;
import jakarta.validation.constraints.NotBlank;

public record SearchUtenteRequest(UtenteDTO utente, @NotBlank String nomeCorso) {
}
