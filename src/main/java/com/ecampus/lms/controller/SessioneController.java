package com.ecampus.lms.controller;

import com.ecampus.lms.dto.response.SessioneDTO;
import com.ecampus.lms.security.PreAuthorizeAdmin;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.security.PreAuthorizeDocente;
import com.ecampus.lms.security.PreAuthorizeStudente;
import com.ecampus.lms.service.SessioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessione")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class SessioneController {

    private final SessioneService service;

    @GetMapping("/summary/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo delle sessioni relative allo studente")
    @PreAuthorizeStudente
    public ResponseEntity<Page<SessioneDTO>> getSessioniStudente(@Parameter(description = "Numero di pagina richiesto")
                                                                 @PathVariable @Min(0) int page,
                                                                 @Parameter(description = "Numero di elementi richiesti")
                                                                 @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.getSessioniStudente(PageRequest.of(page, size)));
    }

    @GetMapping("/summary/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo delle sessioni relative al docente")
    @PreAuthorizeDocente
    public ResponseEntity<Page<SessioneDTO>> getSessioniDocente(@Parameter(description = "Numero di pagina richiesto")
                                                                @PathVariable @Min(0) int page,
                                                                @Parameter(description = "Numero di elementi richiesti")
                                                                @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.getSessioniDocente(PageRequest.of(page, size)));
    }

    @GetMapping("/summary/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo di tutte le sessioni")
    @PreAuthorizeAdmin
    public ResponseEntity<Page<SessioneDTO>> getSessioni(@Parameter(description = "Numero di pagina richiesto")
                                                         @PathVariable @Min(0) int page,
                                                         @Parameter(description = "Numero di elementi richiesti")
                                                         @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.getSessioni(PageRequest.of(page, size)));
    }
}
