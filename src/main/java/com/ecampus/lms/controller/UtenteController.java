package com.ecampus.lms.controller;

import com.ecampus.lms.dto.request.SearchUtenteRequest;
import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.dto.response.UtenteDTO;
import com.ecampus.lms.security.PreAuthorizeAdmin;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.service.UtenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utente")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class UtenteController {

    private final UtenteService utenteService;

    @PostMapping
    @PreAuthorizeAdmin
    @Operation(summary = "Aggiunge un utente al database")
    public ResponseEntity<Void> create(@RequestBody UtenteRequest request) {
        utenteService.create(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search/{size}/{page}")
    @Operation(summary = "Cerca un utente in base ai filtri selezionati")
    public ResponseEntity<Page<UtenteDTO>> searchBy(@RequestBody SearchUtenteRequest request,
                                                  @Parameter(description = "Numero di pagina richiesto")
                                                  @PathVariable @Min(0) int page,
                                                  @Parameter(description = "Numero di elementi richiesti")
                                                  @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(utenteService.searchBy(request, PageRequest.of(page, size)));
    }

    @GetMapping("/list/docenti")
    @Operation(summary = "Ritorna i docenti")
    public ResponseEntity<List<UtenteDTO>> getDocenti(){
        return ResponseEntity.ok(utenteService.getDocenti());
    }


}
