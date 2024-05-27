package com.ecampus.lms.controller;

import com.ecampus.lms.dto.request.SearchProgressiRequest;
import com.ecampus.lms.dto.request.SearchSessioneRequest;
import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.request.UpdateEsitoRequest;
import com.ecampus.lms.dto.response.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/sessione")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class SessioneController {

    private final SessioneService service;

    @GetMapping("/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo delle sessioni relative all'utente")
    public ResponseEntity<Page<SearchSessioneResponse>> getSummary(@Parameter(description = "Numero di pagina richiesto")
                                                                   @PathVariable @Min(0) int page,
                                                                   @Parameter(description = "Numero di elementi richiesti")
                                                                   @PathVariable @Min(1) int size,
                                                                   @RequestParam(required = false) Boolean corrette) {
        return ResponseEntity.ok(service.getSummary(PageRequest.of(page, size), corrette));
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

    @GetMapping("/{id}/iscrizione")
    @Operation(summary = "Iscrive lo studente alla sessione")
    @PreAuthorizeStudente
    public ResponseEntity<SessioneDTO> iscrivi(@Parameter(description = "Id della sessione") @PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.iscrivi(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Dettaglio dellla sessione")
    public ResponseEntity<SessioneDetailsResponse> detail(@Parameter(description = "Id della sessione") @PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.detail(id));
    }

    @PostMapping("/update/esito")
    @Operation(summary = "Update voti degli studenti")
    public ResponseEntity<Void> updateEsiti(@Parameter(description = "Map esito-istanza sessione")
                                            @RequestBody List<UpdateEsitoRequest> esiti) {
        service.updateEsiti(esiti);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/upload/esame", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Upload")
    @PreAuthorizeStudente
    public ResponseEntity<Void> uploadEsameStudente(@RequestPart("idSessione") Integer idSessione, @RequestPart("file") MultipartFile file) throws IOException {
        service.uploadEsameStudente(idSessione, file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/progressi/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo dei progressi in base ai filtri")
    public ResponseEntity<Page<SearchProgressiResponse>> searchProgressi(@RequestBody SearchProgressiRequest request,
                                                                   @Parameter(description = "Numero di pagina richiesto")
                                                                   @PathVariable @Min(0) int page,
                                                                   @Parameter(description = "Numero di elementi richiesti")
                                                                   @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.searchProgressi(request, PageRequest.of(page, size)));
    }

}
