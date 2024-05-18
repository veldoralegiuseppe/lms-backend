package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.SessioneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessioneService {
    Page<SessioneDTO> getSessioni(Pageable pageable);
    void create(SessioneDTO sessione);
    SessioneDTO get(Integer id);
}
