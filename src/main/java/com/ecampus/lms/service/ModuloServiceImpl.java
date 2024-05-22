package com.ecampus.lms.service;

import com.ecampus.lms.dao.CorsoDAO;
import com.ecampus.lms.dao.ModuloDAO;
import com.ecampus.lms.dto.request.CreateModuloRequest;
import com.ecampus.lms.dto.response.ModuloDTO;
import com.ecampus.lms.entity.AttivitaEntity;
import com.ecampus.lms.entity.CorsoEntity;
import com.ecampus.lms.entity.ModuloEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModuloServiceImpl implements ModuloService {

    private final ModuloDAO dao;
    private final CorsoDAO corsoDAO;

    @Override
    public ModuloDTO create(CreateModuloRequest request) {
        final CorsoEntity corso = corsoDAO.findById(request.idCorso()).orElseThrow(() -> new EntityNotFoundException("Corso con id:" + request.idCorso() + " non trovato in archivio"));

        final ModuloEntity modulo = new ModuloEntity();
        modulo.setNome(request.nome());
        modulo.setDescrizione(request.descrizione());
        modulo.setCorso(corso);

        return mapEntityToDTO(dao.save(modulo));
    }

    private ModuloDTO mapEntityToDTO(ModuloEntity modulo){
        final CorsoEntity corso = modulo.getCorso();
        final Set<AttivitaEntity> attivita = modulo.getAttivita();

        final ModuloDTO dto = new ModuloDTO(modulo.getId(),modulo.getNome(), modulo.getDescrizione(), corso.getId(), corso.getNome(),
                attivita != null ? attivita.stream().map(a -> a.getId()).collect(Collectors.toList()) : null);

        return dto;
    }
}
