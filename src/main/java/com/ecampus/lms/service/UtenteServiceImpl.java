package com.ecampus.lms.service;

import com.ecampus.lms.dao.UtenteDAO;
import com.ecampus.lms.dto.request.SearchUtenteRequest;
import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.dto.response.UtenteDTO;
import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.enums.UserRole;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UtenteServiceImpl implements UtenteService{

    private final UtenteDAO dao;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Page<UtenteEntity> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    public List<UtenteEntity> findAll(){
        return dao.findAll();
    }

    @Override
    public Page<UtenteDTO> searchBy(SearchUtenteRequest request, Pageable pageable) {
        return dao.searchBy(
                        request.utente().nome() != null ? request.utente().nome().toUpperCase() : null,
                        request.utente().cognome() != null ? request.utente().cognome().toUpperCase() : null,
                        request.utente().email() != null ? request.utente().email().toUpperCase() : null,
                        request.utente().ruolo() != null ? UserRole.valueOf(request.utente().ruolo()) : null,
                        request.utente().codiceFiscale() != null ? request.utente().codiceFiscale().toUpperCase() : null,
                        request.nomeCorso() != null ? request.nomeCorso().toUpperCase() : null,
                        pageable)
                .map(e -> new UtenteDTO(e.getNome(), e.getCognome(), e.getCodiceFiscale(), e.getEmail(), e.getRuolo().name()));
    }

    @Override
    public Page<UtenteEntity> findByRole(UserRole role, Pageable pageable) {
        return dao.findByRole(UserRole.STUDENTE, pageable);
    }

    @Override
    public UtenteEntity create(UtenteRequest request) {

        final String codiceFiscale = request.codiceFiscale();
        final String username = request.email();

        if(dao.existsByEmailIgnoreCase(username)){
            log.error("create() - utente con email: {} già presente in DUTNE_UTENTE", username);
            throw new EntityExistsException("email: " + username+ " è gia censita nel database");
        }

        if(dao.existsByCodiceFiscale(codiceFiscale)){
            log.error("create() - utente con codice fiscale: {} già presente in DUTNE_UTENTE", codiceFiscale);
            throw new EntityExistsException("il codice fiscale: " + codiceFiscale + " è gia censito nel database");
        }

        log.info("create() - inserimento utente: {}", codiceFiscale);

        final UtenteEntity utenteEntity = new UtenteEntity();
        utenteEntity.setNome(request.nome());
        utenteEntity.setCognome(request.cognome());
        utenteEntity.setCodiceFiscale(request.codiceFiscale());
        utenteEntity.setEmail(request.email());
        utenteEntity.setRuolo(request.ruolo());
        utenteEntity.setPassword(passwordEncoder.encode(request.password()));

        return dao.save(utenteEntity);
    }

    @Override
    public Optional<UtenteEntity> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<UtenteEntity> findByEmail(String email) {
        return dao.findByEmail(email);
    }
}
