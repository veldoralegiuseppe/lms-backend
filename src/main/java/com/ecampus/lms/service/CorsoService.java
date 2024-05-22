package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CreateCorsoRequest;
import com.ecampus.lms.dto.response.CorsoDTO;
import com.ecampus.lms.dto.response.CorsoDetailsDTO;
import com.ecampus.lms.dto.response.CorsoSummaryResponse;
import com.ecampus.lms.entity.CorsoEntity;
import com.ecampus.lms.enums.TipoDettaglioCorso;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CorsoService {
    CorsoSummaryResponse getSummary(Pageable pageable);
    List<CorsoDTO> getAllNomeCorsi();
    List<CorsoDTO> getNomeCorsiByDocente();
    List<CorsoDTO> getCorsiSenzaDocente();
    List<CorsoDTO> getCorsiByStudente(String email);
    CorsoEntity create(CreateCorsoRequest request);
    CorsoDetailsDTO getDettaglio(TipoDettaglioCorso dettaglio, Integer id);
}
