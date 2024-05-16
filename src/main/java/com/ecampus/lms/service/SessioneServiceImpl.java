package com.ecampus.lms.service;

import com.ecampus.lms.dao.SessioneDAO;
import com.ecampus.lms.dto.response.SessioneDTO;
import com.ecampus.lms.entity.SessioneEntity;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class SessioneServiceImpl implements SessioneService{

    private final SessioneDAO dao;

    @Override
    public Page<SessioneDTO> getSessioni(Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username();
        final UserRole role = details.role();

        switch(role){
            case STUDENTE -> { return dao.getSessioniStudente(email, pageable).map(this::mapToResponse); }
            case DOCENTE -> { return dao.getSessioniDocente(email, pageable).map(this::mapToResponse); }
            case ADMIN -> { return dao.getSessioni(pageable).map(this::mapToResponse); }
            default -> { return null;}
        }
    }

    private SessioneDTO mapToResponse(Tuple tuple){
        final String nomeCorso = tuple.get("NOME_CORSO", String.class);
        final SessioneEntity sessione = tuple.get("SESSIONE", SessioneEntity.class);

        return new SessioneDTO(nomeCorso, sessione.getDataOra(), sessione.getTipo());

    }
}
