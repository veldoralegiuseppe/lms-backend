package com.ecampus.lms.service;

import com.ecampus.lms.dao.*;
import com.ecampus.lms.dto.request.SearchSessioneRequest;
import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.response.DocumentaleDTO;
import com.ecampus.lms.dto.response.SearchSessioneResponse;
import com.ecampus.lms.dto.response.SessioneDTO;
import com.ecampus.lms.entity.*;
import com.ecampus.lms.entity.key.IstanzaSessioneEntityId;
import com.ecampus.lms.enums.UserRole;
import com.ecampus.lms.security.SecurityContextDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class SessioneServiceImpl implements SessioneService{

    private final SessioneDAO dao;
    private final CorsoDAO corsoDAO;
    private final DocumentaleService documentaleService;
    private final UtenteDAO utenteDAO;
    private final SessioneSummaryDAO sessioneSummaryDAO;
    private final IstanzaSessioneDAO istanzaSessioneDAO;

    @Override
    public Page<SearchSessioneResponse> getSummary(Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username().toUpperCase();
        final UserRole role = details.role();

        return sessioneSummaryDAO.getSummary(email, role.name(), null, null, LocalDate.now(), null, pageable).map(this::mapToSearchResponse);
    }

    @Override
    @Transactional
    public void create(SessioneRequest dto, MultipartFile file) throws IOException {
        final SessioneEntity sessione = new SessioneEntity();

        if(file != null){
            final DocumentaleEntity provaScritta = documentaleService.store(file);
            sessione.setProvaScritta( provaScritta );
        }
        sessione.setTipo(dto.tipo());
        sessione.setCorso(corsoDAO.findByNomeIgnoreCase(dto.nomeCorso()).orElseThrow(() -> new EntityNotFoundException("Corso '" + dto.nomeCorso() + "' non presente in archivio")));
        sessione.setData(dto.data());

        dao.save(sessione);
    }

    @Override
    public SessioneDTO get(Integer id){
        return dao.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Sessione con id:'" + id +"' non presente in archivio"));
    }

    @Override
    public Page<SearchSessioneResponse> search(SearchSessioneRequest request, Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username().toUpperCase();
        final UserRole role = details.role();

        final String tipo = request.tipo() != null ? request.tipo().toUpperCase() : null;
        final String corso =  request.nomeCorso()!= null ? request.nomeCorso().toUpperCase() : null;
        final LocalDate dataDa = request.dataDa() != null ? request.dataDa() : null;
        final LocalDate dataA = request.dataA() != null ? request.dataA() : null;

        switch(role){
            case STUDENTE -> {return sessioneSummaryDAO.searchStudente(email, corso, tipo, dataDa, dataA, pageable).map(this::mapToSearchResponse);}
            default -> {return sessioneSummaryDAO.getSummary(email, role.name(), corso, tipo, dataDa, dataA, pageable).map(this::mapToSearchResponse);}
        }

    }

    @Override
    @Transactional
    public SessioneDTO iscrivi(Integer id) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username();

        final UtenteEntity studente = utenteDAO.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Utente con id:" + id + " non presente in archivio"));
        final SessioneEntity sessione = dao.findById(id).orElseThrow(() -> new EntityNotFoundException("Sessione con id:" + id + " non presente in archivio"));

        final IstanzaSessioneEntity istanza = new IstanzaSessioneEntity();
        final IstanzaSessioneEntityId idIstanza = new IstanzaSessioneEntityId();
        idIstanza.setIdSessione(sessione.getId());
        idIstanza.setIdStudente(studente.getId());
        istanza.setSessione(sessione);
        istanza.setStudente(studente);
        istanza.setId(idIstanza);

        istanzaSessioneDAO.save(istanza);

        return mapToResponse(sessione);
    }

    /*Utility methods*/
    private SessioneDTO mapToResponse(SessioneEntity entity){
        final String dateTimePattern = "dd/mm/yyyy";
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);


        final SessioneEntity sessione = entity;
        final DocumentaleEntity documentale = sessione.getProvaScritta();

        DocumentaleDTO documentaleDTO = documentale != null ? new DocumentaleDTO(documentale.getId(), documentale.getNome(), documentale.getTipo(), documentale.getDati(),
                                        documentale.getInsertDate(), documentale.getUpdateDate()) : null;

        SessioneDTO sessioneDTO = new SessioneDTO(sessione.getId(), sessione.getCorso().getNome(), sessione.getData(), sessione.getTipo(), documentaleDTO);

        return sessioneDTO;
    }
    private SessioneDTO mapToResponse(Tuple tuple){
        final String dateTimePattern = "dd/MM/yyyy";
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

        final String nomeCorso = tuple.get("NOME_CORSO", String.class);
        final SessioneEntity sessione = tuple.get("SESSIONE", SessioneEntity.class);

        return new SessioneDTO(sessione.getId(), nomeCorso, sessione.getData(), sessione.getTipo(), null);

    }
    private SearchSessioneResponse mapToSearchResponse(Tuple tuple){

        final SessioneSummaryEntity entity = tuple.get("SUMMARY", SessioneSummaryEntity.class);
        final String emailDocente = tuple.get("EMAIL_DOCENTE", String.class);
        final String nomeDocente = tuple.get("NOME_DOCENTE", String.class);
        final String cognomeDocente = tuple.get("COGNOME_DOCENTE", String.class);

        final Integer idSessione = entity.getId().getIdSessione();
        final Integer idCorso = entity.getId().getIdCorso();
        final String nomeCorso = entity.getNomeCorso();
        final LocalDate data = entity.getDataSessione();
        final String tipo = entity.getTipoSessione();
        final Integer numeroStudenti = entity.getNumeroIscritti();

        return new SearchSessioneResponse(idSessione, idCorso, nomeCorso, data, tipo, nomeDocente, cognomeDocente, emailDocente, numeroStudenti);

    }

}
