package com.ecampus.lms.dto.response;

import org.springframework.data.domain.Page;

public record UtenteSummaryResponse(Page<SummaryDTO> summaries) {
}
