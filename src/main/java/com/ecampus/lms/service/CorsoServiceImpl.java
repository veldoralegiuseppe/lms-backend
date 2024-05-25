package com.ecampus.lms.service;

import com.ecampus.lms.beans.DettaglioCorsoBean;
import com.ecampus.lms.dao.CorsoDAO;
import com.ecampus.lms.dao.CorsoSummaryDAO;
import com.ecampus.lms.dao.UtenteDAO;
import com.ecampus.lms.dto.request.CreateCorsoRequest;
import com.ecampus.lms.dto.response.*;
import com.ecampus.lms.entity.CorsoEntity;
import com.ecampus.lms.entity.CorsoSummaryEntity;
import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.enums.TipoDettaglioCorso;
import com.ecampus.lms.enums.UserRole;
import com.ecampus.lms.security.SecurityContextDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorsoServiceImpl implements CorsoService{

    private final CorsoDAO dao;
    private final CorsoSummaryDAO summaryDAO;
    private final UtenteDAO utenteDAO;

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

    @Override
    public List<CorsoDTO> getAllNomeCorsi() {
        return dao.getAllNomeCorsi().stream().map(nome -> new CorsoDTO(null,nome,null,null,null,null,null)).collect(Collectors.toList());
    }

    @Override
    public List<CorsoDTO> getNomeCorsiByDocente() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final UserRole role = details.role();
        final String email = details.username();

        return dao.getAllNomeCorsiByDocente(email).stream().map(nome -> new CorsoDTO(null, nome,null,null,null,null,null)).collect(Collectors.toList());
    }

    @Override
    public List<CorsoDTO> getCorsiSenzaDocente() {
        return dao.findByDocenteNull().stream().map(c -> new CorsoDTO(null, c.getNome(), null ,null, null, null, null)).collect(Collectors.toList());
    }

    @Override
    public List<CorsoDTO> getCorsiByStudente(String mail) {
        String email;
        if(mail == null){
            final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
            email = details.username();
        } else {
            email = mail;
        }

        return dao.findByStudenti_EmailIgnoreCase(email).stream().map(c -> new CorsoDTO(null, c.getNome(), null, null, null, null, null)).collect(Collectors.toList());
    }

    @Override
    public CorsoEntity create(CreateCorsoRequest request) {
        final UtenteEntity docente = utenteDAO.findByEmail(request.emailDocente()).orElseThrow(() -> new EntityNotFoundException("Docente '" + request.emailDocente() + "' non presente in tabella"));

        final CorsoEntity entity = new CorsoEntity();
        entity.setNome(request.nome());
        entity.setDescrizione(request.descrizione());
        entity.setDocente(docente);

        return dao.save(entity);
    }

    @Override
    public CorsoDetailsDTO getDettaglio(TipoDettaglioCorso dettaglio, Integer id) {
       switch (dettaglio){
           case M_A_F -> { return getDettaglio_ModuliAttivitaFile(id); }
           default -> { return null; }
       }
    }

    private CorsoDetailsDTO getDettaglio_ModuliAttivitaFile(Integer id){
        final List<DettaglioCorsoBean> dettaglio = dao.findDettaglio(id);

        if(dettaglio == null || dettaglio.isEmpty())
            throw new EntityNotFoundException("Corso con id:" + id + " non presente in archivio");

        final List<ModuloDetailsDTO> moduli = new ArrayList<>();

        dettaglio.stream()
                .forEach( d -> {

                    if(d.getIdModulo() != null &&  (moduli.isEmpty() || !moduli.get(moduli.size()-1).id().equals(d.getIdModulo()))){
                        moduli.add(new ModuloDetailsDTO(d.getIdModulo(),d.getNomeModulo(),d.getDescrizioneModulo(),d.getIdCorso(), d.getNomeCorso(),new ArrayList<>()));
                    }

                    DocumentaleDTO file = null;
                    if(d.getIdFile() != null) file = new DocumentaleDTO(d.getIdFile(),d.getNomeFile(),d.getContentType(),null,d.getFileInsertDate(),d.getFileUpdateDate());

                    AttivitaDetailsDTO attivita = null;
                    if(d.getIdAttivita() != null) {
                        attivita = new AttivitaDetailsDTO(d.getIdAttivita(), d.getTipoAttivita().name(), d.getSettimanaProgrammata(), d.getIdModulo(), file);
                        moduli.get(moduli.size()-1).attivita().add(attivita);
                    }

                });

        // Compongo il corso
        return new CorsoDetailsDTO(dettaglio.get(0).getIdCorso(), dettaglio.get(0).getNomeCorso(), dettaglio.get(0).getDescrizioneCorso(), moduli, null, null, null);
    }
    private CorsoSummaryDTO mapToResponse(final CorsoSummaryEntity entity){

        final Integer id = entity.getIdCorso();
        final String nomeCorso = entity.getNomeCorso();
        final Integer moduli = entity.getNumeroModuli();
        final Integer sessioni = entity.getNumeroSessioni();
        final Integer studenti = entity.getNumeroStudenti();

        return new CorsoSummaryDTO(id,nomeCorso,moduli,sessioni,studenti);
    }

}
