package com.ecampus.lms.service;

import com.ecampus.lms.dao.CorsoDAO;
import com.ecampus.lms.dto.response.SummaryDTO;
import com.ecampus.lms.dto.response.UtenteSummaryResponse;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorsoServiceImpl implements CorsoService{

    private final CorsoDAO dao;

    @Override
    public UtenteSummaryResponse getStudenteSummary(Integer idStudente) {

        final List<SummaryDTO> summaries = dao.findStudenteSummary(idStudente).stream().map(this::mappingToSummary).collect(Collectors.toList());

        UtenteSummaryResponse response = new UtenteSummaryResponse(idStudente, summaries);
        return response;
    }

    private SummaryDTO mappingToSummary(Tuple tuple){

        final String nomeCorso = tuple.get("NOME_CORSO", String.class);
        final Integer moduli = tuple.get("NUMERO_MODULI", Integer.class);
        final Integer sessioni = tuple.get("NUMERO_SESSIONI", Integer.class);
        final Integer studenti = tuple.get("NUMERO_STUDENTI", Integer.class);

        SummaryDTO summary = new SummaryDTO(nomeCorso, moduli, sessioni, studenti);
        return summary;
    }
}
