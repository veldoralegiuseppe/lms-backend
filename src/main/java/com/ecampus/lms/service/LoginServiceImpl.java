package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CredentialsRequest;
import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService{

    private final UtenteService utenteService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String login(CredentialsRequest credentials) {

        final String username = credentials.username();
        final String password = credentials.password();

        final UtenteEntity utenteEntity = utenteService.findByEmail(username).orElseThrow(() -> {
            log.error("utente: '{}' non presente in tabella Utente", username);
            throw new BadCredentialsException("utente: '" + username + "' non trovato");
        });

        if(!passwordEncoder.matches(password, utenteEntity.getPassword()))
            throw new BadCredentialsException("credenziali di accesso errate");

        final String token = jwtService.buildJwt(username, utenteEntity.getRuolo(), utenteEntity.getNome(), utenteEntity.getCognome(), utenteEntity.getCodiceFiscale());
        log.info("utente: '{}' ha effettuato l'accesso", username);

        return token;
    }

    @Override
    public void logout(String token) {

    }
}
