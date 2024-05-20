package com.ecampus.lms.controller;

import com.ecampus.lms.dto.request.SearchSessioneRequest;
import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.response.MessageDTO;
import com.ecampus.lms.dto.response.SearchSessioneResponse;
import com.ecampus.lms.dto.response.SessioneDTO;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.security.PreAuthorizeDocente;
import com.ecampus.lms.service.SessioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/sessione")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class SessioneController {

    private final SessioneService service;

    @GetMapping("/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo delle sessioni relative allo studente")
    public ResponseEntity<Page<SessioneDTO>> getSessioni(@Parameter(description = "Numero di pagina richiesto")
                                                         @PathVariable @Min(0) int page,
                                                         @Parameter(description = "Numero di elementi richiesti")
                                                         @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.getSessioni(PageRequest.of(page, size)));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Crea una nuova sessione")
    @PreAuthorizeDocente
    public ResponseEntity<MessageDTO> create(@RequestPart("sessione") SessioneRequest request, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        service.create(request, file);
        return ResponseEntity.ok(new MessageDTO("Sessione creata con successo"));
    }

    @PostMapping("/search/{size}/{page}")
    @Operation(summary = "Ricerca sessioni")
    public ResponseEntity<Page<SearchSessioneResponse>> search(@RequestBody SearchSessioneRequest request,
                                                               @Parameter(description = "Numero di pagina richiesto")
                                                               @PathVariable @Min(0) int page,
                                                               @Parameter(description = "Numero di elementi richiesti")
                                                               @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.search(request, PageRequest.of(page, size)));
    }
}
