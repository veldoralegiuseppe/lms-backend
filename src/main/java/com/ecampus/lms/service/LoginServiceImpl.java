package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CredentialsRequest;
import com.ecampus.lms.entity.Utente;
import com.ecampus.lms.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService{

    private final UtenteService utenteService;
    private final JwtService jwtService;


    @Override
    public String login(CredentialsRequest credentials) {


        final String username = credentials.username();

        final Utente utente = utenteService.findByEmail(username).orElseThrow(() -> {
            log.error("utente: '{}' non presente in tabella Utente", username);
            throw new BadCredentialsException("utente: '" + username + "' non trovato");
        });

        final String token = jwtService.buildJwt(username, utente.getRuolo(), utente.getNome(), utente.getCognome());
        log.info("utente: '{}' ha effettuato l'accesso", username);

        return token;
    }

    @Override
    public void logout(String token) {

    }
}
