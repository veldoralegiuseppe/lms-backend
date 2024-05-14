package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.entity.UtenteEntity;
import com.ecampus.lms.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UtenteService {
    Page<UtenteEntity> findAll(Pageable pageable);

    Page<UtenteEntity> findByRole(UserRole role, Pageable pageable);

    UtenteEntity create(UtenteRequest request);

    Optional<UtenteEntity> findById(Integer id);

    Optional<UtenteEntity> findByEmail(String email);

    List<UtenteEntity> findAll();
}
