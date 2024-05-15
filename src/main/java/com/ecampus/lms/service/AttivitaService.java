package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.AttivitaSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface AttivitaService {
    AttivitaSummaryResponse getStudenteSummary(Pageable pageable);
}
