package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.response.SessioneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SessioneService {
    Page<SessioneDTO> getSessioni(Pageable pageable);
    void create(SessioneRequest sessione, MultipartFile file) throws IOException;
    SessioneDTO get(Integer id);
}
