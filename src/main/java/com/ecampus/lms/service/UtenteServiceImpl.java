package com.ecampus.lms.service;

import com.ecampus.lms.dao.UtenteDAO;
import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.entity.Utente;
import com.ecampus.lms.enums.UserRole;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UtenteServiceImpl implements UtenteService{

    private final UtenteDAO dao;


    @Override
    public Page<Utente> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    public List<Utente> findAll(){
        return dao.findAll();
    }

    @Override
    public Page<Utente> findByRole(UserRole role, Pageable pageable) {
        return dao.findByRole(UserRole.STUDENTE, pageable);
    }

    @Override
    public Utente create(UtenteRequest request) {

        final String codiceFiscale = request.codiceFiscale();

        if(dao.existsByCodiceFiscale(codiceFiscale)){
            log.error("create() - utente con codice fiscale: {} già presente in DUTNE_UTENTE", codiceFiscale);
            throw new EntityExistsException("il codice fiscale: " + codiceFiscale + " è gia censito nel database");
        }

        log.info("create() - inserimento utente: {}", codiceFiscale);

        final Utente utente = new Utente();
        utente.setNome(request.nome());
        utente.setCognome(request.cognome());
        utente.setCodiceFiscale(request.codiceFiscale());
        utente.setEmail(request.email());
        utente.setRuolo(request.ruolo());

        return dao.save(utente);
    }

    @Override
    public Optional<Utente> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<Utente> findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
