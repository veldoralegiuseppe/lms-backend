package com.ecampus.lms.service;

import com.ecampus.lms.dao.AttivitaDAO;
import com.ecampus.lms.dao.ModuloDAO;
import com.ecampus.lms.dto.request.CreateAttivitaRequest;
import com.ecampus.lms.dto.response.AttivitaDTO;
import com.ecampus.lms.dto.response.AttivitaSummaryDTO;
import com.ecampus.lms.dto.response.AttivitaSummaryResponse;
import com.ecampus.lms.entity.AttivitaEntity;
import com.ecampus.lms.entity.DocumentaleEntity;
import com.ecampus.lms.entity.ModuloEntity;
import com.ecampus.lms.enums.UserRole;
import com.ecampus.lms.security.SecurityContextDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttivitaServiceImpl implements AttivitaService {

    private final AttivitaDAO dao;
    private final ModuloDAO moduloDAO;
    private final DocumentaleService documentaleService;

    @Override
    public AttivitaSummaryResponse getAttivitaStudente(Pageable pageable) {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final UserRole role = details.role();
        final String email = details.username();

        log.info("recupero il sommario delle attivita per l'utente: '{}'", email);
        final Page<AttivitaSummaryDTO> summaries = dao.findStudenteSummary(email, pageable).map(this::mappingToResponse);
        return new AttivitaSummaryResponse(summaries);
    }

    @Override
    @Transactional
    public AttivitaDTO create(CreateAttivitaRequest request, MultipartFile documento) throws FileUploadException {
        final ModuloEntity modulo = moduloDAO.findById(request.idModulo()).orElseThrow(() -> new EntityNotFoundException("Modulo con id:" + request.idModulo() + " non presente in archivio"));

        DocumentaleEntity file;
        try {
            file = documentaleService.store(documento);
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }

        final AttivitaEntity attivita = new AttivitaEntity();
        attivita.setModulo(modulo);
        attivita.setTipo(request.tipo());
        attivita.setSettimanaProgrammata(request.settimanaProgrammata());
        attivita.setFile(file);

        return mapEntityToDAO(dao.save(attivita));
    }

    private AttivitaSummaryDTO mappingToResponse(Tuple tuple) {
        final AttivitaEntity attivita = tuple.get("ATTIVITA", AttivitaEntity.class);
        final String nomeCorso = tuple.get("NOME_CORSO", String.class);

        return new AttivitaSummaryDTO(nomeCorso, attivita.getTipo().name(), attivita.getSettimanaProgrammata());
    }

    private AttivitaDTO mapEntityToDAO(AttivitaEntity attivita){
        final ModuloEntity modulo = attivita.getModulo();
        final DocumentaleEntity file = attivita.getFile();

        return new AttivitaDTO(attivita.getTipo().name(), attivita.getSettimanaProgrammata(), modulo.getId(), attivita.getId(), file.getId());
    }

}
