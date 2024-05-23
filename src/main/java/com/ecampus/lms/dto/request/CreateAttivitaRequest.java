package com.ecampus.lms.dto.request;

import com.ecampus.lms.dto.response.DocumentaleDTO;
import com.ecampus.lms.enums.TipoAttivita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAttivitaRequest(@NotBlank TipoAttivita tipo,
                                    @NotBlank Integer settimanaProgrammata,
                                    @NotNull Integer idModulo,
                                    @NotNull Integer idCorso) {
}
