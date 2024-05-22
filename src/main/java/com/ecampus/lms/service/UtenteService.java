package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.SearchUtenteRequest;
import com.ecampus.lms.dto.request.UtenteRequest;
import com.ecampus.lms.dto.response.UtenteDTO;
import com.ecampus.lms.entity.UtenteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UtenteService {

    UtenteEntity create(UtenteRequest request);

    Optional<UtenteEntity> findByEmail(String email);

    Page<UtenteDTO> searchBy(SearchUtenteRequest request, Pageable pageable);

    List<UtenteDTO> getDocenti();
}
