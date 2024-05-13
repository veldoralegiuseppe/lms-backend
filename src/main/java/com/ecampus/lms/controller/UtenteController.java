package com.ecampus.lms.controller;

import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.security.PreAuthorizeAdmin;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.service.UtenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> create(@RequestBody UtenteRequest request){
        utenteService.create(request);
        return ResponseEntity.ok().build();
    }


}
