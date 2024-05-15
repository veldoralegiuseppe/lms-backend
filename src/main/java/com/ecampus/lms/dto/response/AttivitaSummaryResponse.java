package com.ecampus.lms.dto.response;

import org.springframework.data.domain.Page;

public record AttivitaSummaryResponse(Page<AttivitaSummaryDTO> summaries) {
}
