package com.ecampus.lms.controller;

import com.ecampus.lms.dto.response.AttivitaSummaryResponse;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.security.PreAuthorizeStudente;
import com.ecampus.lms.service.AttivitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attivita")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class AttivitaController {

    private final AttivitaService service;

    @GetMapping("/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo delle attivita dello studente")
    @PreAuthorizeStudente
    public ResponseEntity<AttivitaSummaryResponse> getAttivitaStudente(@Parameter(description = "Numero di pagina richiesto")
                                                                      @PathVariable @Min(0) int page,
                                                                      @Parameter(description = "Numero di elementi richiesti")
                                                                      @PathVariable @Min(1) int size){
        return ResponseEntity.ok(service.getAttivitaStudente(PageRequest.of(page, size)));
    }
}
