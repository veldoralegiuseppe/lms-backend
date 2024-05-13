package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.entity.Utente;
import com.ecampus.lms.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UtenteService {
    Page<Utente> findAll(Pageable pageable);

    Page<Utente> findByRole(UserRole role, Pageable pageable);

    Utente create(UtenteRequest request);

    Optional<Utente> findById(Integer id);

    Optional<Utente> findByEmail(String email);

    List<Utente> findAll();
}
