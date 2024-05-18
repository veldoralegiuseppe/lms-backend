package com.ecampus.lms.service;

import com.ecampus.lms.dao.CorsoDAO;
import com.ecampus.lms.dao.SessioneDAO;
import com.ecampus.lms.dao.UtenteDAO;
import com.ecampus.lms.dto.response.DocumentaleDTO;
import com.ecampus.lms.dto.response.SessioneDTO;
import com.ecampus.lms.entity.DocumentaleEntity;
import com.ecampus.lms.entity.SessioneEntity;
import com.ecampus.lms.enums.UserRole;
import com.ecampus.lms.security.SecurityContextDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class SessioneServiceImpl implements SessioneService{

    private final SessioneDAO dao;
    private final UtenteDAO utenteDAO;
    private final CorsoDAO corsoDAO;

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

    @Override
    public void create(SessioneDTO dto) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final UserRole role = details.role();
        final String email = details.username();

        final DocumentaleEntity file = dto.provaScritta() != null ? new DocumentaleEntity() : null;
        final SessioneEntity sessione = new SessioneEntity();

        if(file != null){
            file.setNome(dto.provaScritta().nome());
            file.setTipo(dto.provaScritta().contentType());
            file.setDati(dto.provaScritta().data());
            file.setUtente( utenteDAO.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Utente '"+ email + "' non presente in archivio")) );
            sessione.setProvaScritta(file);
        }
        sessione.setTipo(dto.tipo());
        sessione.setCorso(corsoDAO.findByNomeIgnoreCase(dto.nomeCorso()).orElseThrow(() -> new EntityNotFoundException("Corso '" + dto.nomeCorso() + "' non presente in archivio")));
        sessione.setData(new Date(dto.data()));

        dao.save(sessione);
    }

    @Override
    public SessioneDTO get(Integer id){
        return dao.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Sessione con id:'" + id +"' non presente in archivio"));
    }

    /*Utility methods*/
    private SessioneDTO mapToResponse(SessioneEntity entity){
        final String dateTimePattern = "dd/MM/yyyy";
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

        final SessioneEntity sessione = entity;
        final DocumentaleEntity documentale = sessione.getProvaScritta();

        DocumentaleDTO documentaleDTO = documentale != null ? new DocumentaleDTO(documentale.getId(), documentale.getNome(), documentale.getTipo(), documentale.getDati(),
                                        documentale.getInsertDate().toLocalDate().format(dateTimeFormatter), documentale.getUpdateDate().toLocalDate().format(dateTimeFormatter)) : null;

        SessioneDTO sessioneDTO = new SessioneDTO(sessione.getId(), sessione.getCorso().getNome(), dateTimeFormatter.format(sessione.getData().toInstant()), sessione.getTipo(), documentaleDTO);

        return sessioneDTO;
    }
    private SessioneDTO mapToResponse(Tuple tuple){
        final String dateTimePattern = "dd/MM/yyyy";
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

        final String nomeCorso = tuple.get("NOME_CORSO", String.class);
        final SessioneEntity sessione = tuple.get("SESSIONE", SessioneEntity.class);

        return new SessioneDTO(sessione.getId(), nomeCorso, dateTimeFormatter.format(sessione.getData().toInstant()), sessione.getTipo(), null);

    }

}
