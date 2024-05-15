package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.CorsoSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface CorsoService {
    CorsoSummaryResponse getSummary(Pageable pageable);
}
