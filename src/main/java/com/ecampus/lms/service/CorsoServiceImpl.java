package com.ecampus.lms.service;

import com.ecampus.lms.dao.CorsoDAO;
import com.ecampus.lms.dto.response.CorsoSummaryDTO;
import com.ecampus.lms.dto.response.CorsoSummaryResponse;
import com.ecampus.lms.enums.UserRole;
import com.ecampus.lms.security.SecurityContextDetails;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorsoServiceImpl implements CorsoService{

    private final CorsoDAO dao;

    @Override
    public CorsoSummaryResponse getSummary(Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final UserRole role = details.role();
        final String email = details.username();

        log.info("recupero il sommario dei corsi per l'utente: '{}'", email);
        final Page<CorsoSummaryDTO> summaries = dao.findSummary(role, email, pageable).map(t -> mappingToSummary(t, role));

        CorsoSummaryResponse response = new CorsoSummaryResponse(summaries);
        return response;
    }

    private CorsoSummaryDTO mappingToSummary(final Tuple tuple, final UserRole role){

        final String nomeCorso = tuple.get("NOME_CORSO", String.class);
        final Long moduli = tuple.get("NUMERO_MODULI", Long.class);
        final Long sessioni = tuple.get("NUMERO_SESSIONI", Long.class);
        final Long studenti = tuple.get("NUMERO_STUDENTI", Long.class);

        switch (role){
            case DOCENTE, ADMIN -> {
                return new CorsoSummaryDTO(nomeCorso, moduli != null ? moduli.intValue() : 0, sessioni != null ? sessioni.intValue() : 0, studenti != null ? studenti.intValue() : 0);
            }
            case STUDENTE -> {
                return new CorsoSummaryDTO(nomeCorso, moduli != null ? moduli.intValue() : 0, sessioni != null ? sessioni.intValue() : 0, null);
            }
            default -> {
                return null;
            }
        }

    }
}
