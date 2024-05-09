package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.entity.Utente;
import com.ecampus.lms.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UtenteService {
    Page<Utente> findAll(Pageable pageable);

    Page<Utente> findByRole(UserRole role, Pageable pageable);

    Utente create(UtenteRequest request);

    Optional<Utente> findById(Integer id);
}
