package com.ecampus.lms.controller;

import com.ecampus.lms.dto.request.CredentialsRequest;
import com.ecampus.lms.dto.request.XTokenRequest;
import com.ecampus.lms.security.SecurityContextDetails;
import com.ecampus.lms.service.LoginService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<XTokenRequest> login(@Parameter(description = "Credentials")
                                               @Valid @RequestBody CredentialsRequest credentials){
        final String xToken = loginService.login(credentials);
        return ResponseEntity.ok(new XTokenRequest(xToken));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(final Authentication authentication){

        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String token = details.token();

        loginService.logout(token);

        return ResponseEntity.ok().build();
    }

}
