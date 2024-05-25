package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.SearchSessioneRequest;
import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.response.SearchSessioneResponse;
import com.ecampus.lms.dto.response.SessioneDetailsResponse;
import com.ecampus.lms.dto.response.SessioneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SessioneService {
    Page<SearchSessioneResponse> getSummary(Pageable pageable);
    void create(SessioneRequest sessione, MultipartFile file) throws IOException;
    SessioneDTO get(Integer id);
    Page<SearchSessioneResponse> search(SearchSessioneRequest request, Pageable pageable);
    SessioneDTO iscrivi(Integer id);
    SessioneDetailsResponse detail(Integer id);
}
