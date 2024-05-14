package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.SummaryDTO;
import com.ecampus.lms.dto.response.UtenteSummaryResponse;

import java.util.List;

public interface CorsoService {

    UtenteSummaryResponse getStudenteSummary(Integer idStudente);
}
