package com.ecampus.lms.service;

import com.ecampus.lms.dao.CorsoDAO;
import com.ecampus.lms.dao.CorsoSummaryDAO;
import com.ecampus.lms.dto.response.CorsoSummaryDTO;
import com.ecampus.lms.dto.response.CorsoSummaryResponse;
import com.ecampus.lms.entity.CorsoSummaryEntity;
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
    private final CorsoSummaryDAO summaryDAO;

    @Override
    public CorsoSummaryResponse getSummary(Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final UserRole role = details.role();
        final String email = details.username();

        log.info("recupero il sommario dei corsi per l'utente: '{}'", email);

        switch (role){
            case DOCENTE -> {
                final Page<CorsoSummaryDTO> summaries = summaryDAO.getDocenteSummary(email, pageable).map(this::mapToResponse);
                return new CorsoSummaryResponse(summaries);
            }
            case ADMIN -> {
                final Page<CorsoSummaryDTO> summaries = summaryDAO.findAll(pageable).map(this::mapToResponse);
                return new CorsoSummaryResponse(summaries);
            }
            case STUDENTE -> {
                final Page<CorsoSummaryDTO> summaries = summaryDAO.getStudenteSummary(email, pageable).map(this::mapToResponse);
                return new CorsoSummaryResponse(summaries);
            }
            default -> {
                return null;
            }
        }
    }

    private CorsoSummaryDTO mapToResponse(final CorsoSummaryEntity entity){

        final String nomeCorso = entity.getNomeCorso();
        final Integer moduli = entity.getNumeroModuli();
        final Integer sessioni = entity.getNumeroSessioni();
        final Integer studenti = entity.getNumeroStudenti();

        return new CorsoSummaryDTO(nomeCorso,moduli,sessioni,studenti);
    }
}
