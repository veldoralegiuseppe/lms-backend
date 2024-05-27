package com.ecampus.lms.service;

import com.ecampus.lms.dao.*;
import com.ecampus.lms.dto.request.SearchProgressiRequest;
import com.ecampus.lms.dto.request.SearchSessioneRequest;
import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.request.UpdateEsitoRequest;
import com.ecampus.lms.dto.response.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Page<SearchSessioneResponse> getSummary(Pageable pageable, Boolean corrette) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username().toUpperCase();
        final UserRole role = details.role();

        return sessioneSummaryDAO.getSummary(email, corrette, role.name(),  null, null, LocalDate.now(), null, pageable).map(this::mapToSearchResponse);
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
            default -> {return sessioneSummaryDAO.getSummary(email, null, role.name(), corso, tipo, dataDa, dataA, pageable).map(this::mapToSearchResponse);}
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

    @Override
    public SessioneDetailsResponse detail(Integer id) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username();
        final UserRole role = details.role();

        switch (role){
            case DOCENTE, STUDENTE, ADMIN -> {return mapToDetailsResponse(dao.getSessionDetails(id, role.name(), email), id);}
            default -> {return null;}
        }
    }

    @Override
    public void updateEsiti(List<UpdateEsitoRequest> esiti) {
        esiti.forEach(request -> {
            final IstanzaSessioneEntityId idIstanza = new IstanzaSessioneEntityId();
            idIstanza.setIdSessione(request.idSessione());
            idIstanza.setIdStudente(request.idStudente());

            istanzaSessioneDAO.updateEsito(request.esito(), List.of(idIstanza));
        });
    }

    @Override
    @Transactional
    public void uploadEsameStudente(Integer idSessione, MultipartFile file) throws IOException {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username();
        final UserRole role = details.role();

       final IstanzaSessioneEntity istanza = istanzaSessioneDAO.findIstanzaSessione(idSessione, email)
               .orElseThrow(() -> new EntityNotFoundException("Istanza della sessione con id: '"+ idSessione + "' per l'utente: '" + email + "' non trovata"));

       final DocumentaleEntity esame = documentaleService.store(file);

       istanzaSessioneDAO.updateProvaScritta(esame, istanza.getId());
    }

    @Override
    public Page<SearchProgressiResponse> searchProgressi(SearchProgressiRequest request, Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final String email = details.username();
        final UserRole role = details.role();

        final String nome = request.nome() != null ? request.nome().toUpperCase() : null;
        final String cognome = request.cognome() != null ? request.cognome().toUpperCase() : null;
        final String codiceFiscale = request.codiceFiscale() != null ? request.codiceFiscale().toUpperCase() : null;
        final String tipoSessione = request.tipoSessione() != null ? request.tipoSessione().toUpperCase() : null;
        final String nomeCorso = request.nomeCorso() != null ? request.nomeCorso().toUpperCase() : null;

        switch(role){
            case STUDENTE -> {return sessioneSummaryDAO.searchProgressi(email, nome, cognome, codiceFiscale, nomeCorso, tipoSessione, request.dataDa(), request.dataA(), pageable).map(this::matToSearchProgressiResponse);}
            case ADMIN,DOCENTE -> {return sessioneSummaryDAO.searchProgressi(null, nome, cognome, codiceFiscale, nomeCorso, tipoSessione, request.dataDa(), request.dataA(), pageable).map(this::matToSearchProgressiResponse);}
            default -> {return null;}
        }

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
        final Integer proveConsegnate = entity.getProveConsegnate();
        final Integer proveCorrette = entity.getProveCorrette();


        return new SearchSessioneResponse(idSessione, idCorso, nomeCorso, data, tipo, nomeDocente, cognomeDocente, emailDocente, numeroStudenti, proveConsegnate, proveCorrette);

    }
    private SessioneDetailsResponse mapToDetailsResponse(List<Tuple> tuple, Integer idSessione){

        final Tuple tupla = tuple.get(0);
        final String nomeCorso = tupla.get("NOME_CORSO", String.class);
        final String tipoSessione = tupla.get("TIPO_SESSIONE", String.class);
        final String idProvaSomministrata = tupla.get("ID_PROVA_SOMMINISTRATA", String.class);
        final String nomeDocente = tupla.get("NOME_DOCENTE", String.class);
        final String cognomeDocente = tupla.get("COGNOME_DOCENTE", String.class);
        final String emailDocente = tupla.get("EMAIL_DOCENTE", String.class);
        final Integer idDocente = tupla.get("ID_DOCENTE", Integer.class);
        final Integer idCorso = tupla.get("ID_CORSO", Integer.class);
        final Integer numeroIscritti = tupla.get("NUMERO_ISCRITTI", Integer.class);
        final LocalDate dataSessione = tupla.get("DATA_SESSIONE", LocalDate.class);
        final String nomeProvaSomministrata = tupla.get("NOME_PROVA_SOMMINISTRATA", String.class);
        final String contentTypeProvaSomministrata = tupla.get("CONTENT_TYPE_PROVA_SOMMINISTRATA", String.class);


        final List<IstanzaSessioneDTO> esami = tuple.stream().map(t -> {
            final String nomeStudente = t.get("NOME_STUDENTE", String.class);
            final String cognomeStudente = t.get("COGNOME_STUDENTE", String.class);
            final String emailStudente = t.get("EMAIL_STUDENTE", String.class);
            final Integer idStudente = t.get("ID_STUDENTE", Integer.class);
            final String idFileStudente = t.get("ID_PROVA_STUDENTE", String.class);
            final String codiceFiscaleStudente = t.get("CODICE_FISCALE_STUDENTE", String.class);
            final String nomeFileStudente = t.get("NOME_PROVA_STUDENTE", String.class);
            final String esito = t.get("ESITO_STUDENTE", String.class);
            final String contentType = t.get("CONTENT_TYPE_PROVA_STUDENTE", String.class);


            return new IstanzaSessioneDTO(idSessione, idStudente,nomeStudente,cognomeStudente,codiceFiscaleStudente,emailStudente,idFileStudente,nomeFileStudente,contentType,esito);
        }).collect(Collectors.toList());

        return new SessioneDetailsResponse(idCorso,idDocente,nomeDocente,cognomeDocente,emailDocente,nomeCorso,idSessione,tipoSessione,dataSessione,numeroIscritti,idProvaSomministrata,nomeProvaSomministrata,contentTypeProvaSomministrata,esami);
    }
    private SearchProgressiResponse matToSearchProgressiResponse(Tuple tuple){
        final SessioneSummaryEntity entity = tuple.get("SUMMARY", SessioneSummaryEntity.class);
        final String nome = tuple.get("NOME", String.class);
        final String cognome = tuple.get("COGNOME", String.class);
        final String codiceFiscale = tuple.get("CODICE_FISCALE", String.class);
        final String esito = tuple.get("ESITO", String.class);


        return new SearchProgressiResponse(nome,cognome,codiceFiscale,entity.getNomeCorso(),esito,entity.getTipoSessione(),entity.getDataSessione());
    }

}
