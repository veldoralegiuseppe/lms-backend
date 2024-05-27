package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.SearchProgressiRequest;
import com.ecampus.lms.dto.request.SearchSessioneRequest;
import com.ecampus.lms.dto.request.SessioneRequest;
import com.ecampus.lms.dto.request.UpdateEsitoRequest;
import com.ecampus.lms.dto.response.SearchProgressiResponse;
import com.ecampus.lms.dto.response.SearchSessioneResponse;
import com.ecampus.lms.dto.response.SessioneDetailsResponse;
import com.ecampus.lms.dto.response.SessioneDTO;
import com.ecampus.lms.entity.key.IstanzaSessioneEntityId;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SessioneService {
    Page<SearchSessioneResponse> getSummary(Pageable pageable, Boolean corrette);
    void create(SessioneRequest sessione, MultipartFile file) throws IOException;
    SessioneDTO get(Integer id);
    Page<SearchSessioneResponse> search(SearchSessioneRequest request, Pageable pageable);
    SessioneDTO iscrivi(Integer id);
    SessioneDetailsResponse detail(Integer id);
    void updateEsiti(List<UpdateEsitoRequest> esiti);
    void uploadEsameStudente(Integer idSessione, MultipartFile file) throws IOException;
    Page<SearchProgressiResponse> searchProgressi(SearchProgressiRequest request, Pageable pageable);
}
