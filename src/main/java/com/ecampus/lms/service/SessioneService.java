package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.SessioneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessioneService {
    Page<SessioneDTO> getSessioniStudente(Pageable pageable);
    Page<SessioneDTO> getSessioniDocente(Pageable pageable);
    Page<SessioneDTO> getSessioni(Pageable pageable);
}
