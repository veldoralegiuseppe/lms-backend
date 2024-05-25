package com.ecampus.lms.controller;

import com.ecampus.lms.dto.request.CreateAttivitaRequest;
import com.ecampus.lms.dto.request.CreateCorsoRequest;
import com.ecampus.lms.dto.request.CreateModuloRequest;
import com.ecampus.lms.dto.response.*;
import com.ecampus.lms.enums.TipoDettaglioCorso;
import com.ecampus.lms.security.PreAuthorizeAdmin;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.security.PreAuthorizeDocente;
import com.ecampus.lms.service.AttivitaService;
import com.ecampus.lms.service.CorsoService;
import com.ecampus.lms.service.ModuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/corso")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
public class CorsoController {

    private final CorsoService service;
    private final ModuloService moduloService;
    private final AttivitaService attivitaService;

    @GetMapping("/summary/{size}/{page}")
    @Operation(summary = "Restituisce il riepilogo corsi/moduli/sessioni/studenti in funzione del ruolo")
    public ResponseEntity<CorsoSummaryResponse> getSummary(@Parameter(description = "Numero di pagina richiesto")
                                                           @PathVariable @Min(0) int page,
                                                           @Parameter(description = "Numero di elementi richiesti")
                                                           @PathVariable @Min(1) int size) {
        return ResponseEntity.ok(service.getSummary(PageRequest.of(page, size)));
    }

    @GetMapping("dettaglio/{filtro}/{id}")
    @Operation(summary = "Restituisce il dettaglio del corso in base ai filtri specificati")
    public ResponseEntity<CorsoDetailsDTO> getCorsoDettagliato(@Parameter(description = "Numero di pagina richiesto")
                                                                   @PathVariable("filtro") TipoDettaglioCorso dettaglio,
                                                               @Parameter(description = "Numero di pagina richiesto")
                                                               @PathVariable("id") @Min(1) Integer id) {
        return ResponseEntity.ok(service.getDettaglio(dettaglio, id));
    }

    @GetMapping("/list/nome")
    @Operation(summary = "Restituisce i nomi di tutti i corsi")
    public ResponseEntity<List<CorsoDTO>> getAllNomeCorsi() {
        return ResponseEntity.ok(service.getAllNomeCorsi());
    }

    @GetMapping("/list/docente/nome")
    @Operation(summary = "Restituisce i nomi di tutti i corsi afferenti al docente")
    public ResponseEntity<List<CorsoDTO>> getNomeCorsiByDocente() {
        return ResponseEntity.ok(service.getNomeCorsiByDocente());
    }

    @GetMapping("/list/cattedra-libera")
    @Operation(summary = "Restituisce i nomi di tutti i corsi cui non è stato assegnato un docente")
    public ResponseEntity<List<CorsoDTO>> getCorsiSenzaDocente() {
        return ResponseEntity.ok(service.getCorsiSenzaDocente());
    }

    @PostMapping("/list/studente")
    @Operation(summary = "Restituisce i corsi afferenti allo studente")
    public ResponseEntity<List<CorsoDTO>> getCorsiByStudente(@RequestBody String email) {
        return ResponseEntity.ok(service.getCorsiByStudente(email));
    }

    @GetMapping("/list/studente")
    @Operation(summary = "Restituisce i corsi afferenti allo studente")
    public ResponseEntity<List<CorsoDTO>> getCorsiByStudente() {
        return ResponseEntity.ok(service.getCorsiByStudente(null));
    }

    @PostMapping
    @Operation(summary = "Crea un corso")
    @PreAuthorizeAdmin
    public ResponseEntity<Void> create(@RequestBody CreateCorsoRequest request) {
        service.create(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modulo")
    @Operation(summary = "Crea un modulo")
    @PreAuthorizeDocente
    public ResponseEntity<ModuloDTO> createModulo(@RequestBody CreateModuloRequest request) {
        return ResponseEntity.ok(moduloService.create(request));
    }

    @PostMapping(value = "/attivita",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un attività")
    @PreAuthorizeDocente
    public ResponseEntity<AttivitaDTO> createAttivita(@RequestPart("attivita") CreateAttivitaRequest request, @RequestPart("file") MultipartFile file) throws FileUploadException {
        return ResponseEntity.ok(attivitaService.create(request, file));
    }

}
