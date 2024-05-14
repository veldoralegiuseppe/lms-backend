package com.ecampus.lms.dto.response;

import java.util.List;

public record UtenteSummaryResponse(Integer idUtente, List<SummaryDTO> summaries) {
}
