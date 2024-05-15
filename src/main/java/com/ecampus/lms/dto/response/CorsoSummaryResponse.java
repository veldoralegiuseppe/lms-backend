package com.ecampus.lms.dto.response;

import org.springframework.data.domain.Page;

public record CorsoSummaryResponse(Page<CorsoSummaryDTO> summaries) {
}
