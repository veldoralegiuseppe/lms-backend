package com.ecampus.lms.controller;

import com.ecampus.lms.dto.response.CorsoDTO;
import com.ecampus.lms.dto.response.CorsoSummaryResponse;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.service.CorsoService;
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

import java.util.List;

@RestController
@RequestMapping("/api/corso")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class CorsoController {

    private final CorsoService service;

    @GetMapping("/summary/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo corsi/moduli/sessioni/studenti in funzione del ruolo")
    public ResponseEntity<CorsoSummaryResponse> getSummary(@Parameter(description = "Numero di pagina richiesto")
                                                            @PathVariable @Min(0) int page,
                                                           @Parameter(description = "Numero di elementi richiesti")
                                                            @PathVariable @Min(1) int size){
        return ResponseEntity.ok(service.getSummary(PageRequest.of(page, size)));
    }

    @GetMapping("/list/nome")
    @Operation(summary = "Restituisce i nomi di tutti i corsi")
    public ResponseEntity<List<CorsoDTO>> getAllNomeCorsi(){
        return ResponseEntity.ok(service.getAllNomeCorsi());
    }

    @GetMapping("/list/docente/nome")
    @Operation(summary = "Restituisce i nomi di tutti i corsi afferenti al docente")
    public ResponseEntity<List<CorsoDTO>> getNomeCorsiByDocente(){
        return ResponseEntity.ok(service.getNomeCorsiByDocente());
    }

}
