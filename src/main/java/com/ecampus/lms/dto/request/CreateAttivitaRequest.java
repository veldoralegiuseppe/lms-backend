package com.ecampus.lms.dto.request;

import com.ecampus.lms.enums.TipoAttivita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAttivitaRequest(@NotNull Integer idModulo,
                                    @NotBlank @NotNull TipoAttivita tipo,
                                    @NotNull Integer settimanaProgrammata) {
}
