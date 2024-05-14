package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.UtenteSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface CorsoService {

    UtenteSummaryResponse getSummary(Pageable pageable);
}
