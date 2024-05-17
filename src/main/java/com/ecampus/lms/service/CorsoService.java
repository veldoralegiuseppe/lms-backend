package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.CorsoDTO;
import com.ecampus.lms.dto.response.CorsoSummaryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CorsoService {
    CorsoSummaryResponse getSummary(Pageable pageable);
    List<CorsoDTO> getAllNomeCorsi();
}
