package com.ecampus.lms.service;

import com.ecampus.lms.dao.DocumentaleDAO;
import com.ecampus.lms.dao.UtenteDAO;
import com.ecampus.lms.dto.response.DocumentaleDTO;
import com.ecampus.lms.entity.DocumentaleEntity;
import com.ecampus.lms.enums.UserRole;
import com.ecampus.lms.security.SecurityContextDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentaleServiceImpl implements DocumentaleService{

    private final DocumentaleDAO dao;
    private final UtenteDAO utenteDAO;

    @Transactional
    public DocumentaleEntity store(MultipartFile file) throws IOException {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        final UserRole role = details.role();
        final String email = details.username();

        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final String contentType = file.getContentType();
        final byte[] data = file.getBytes();

        DocumentaleEntity entity = new DocumentaleEntity();
        entity.setNome(fileName);
        entity.setTipo(contentType);
        entity.setDati(data);
        entity.setUtente( utenteDAO.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Utente '"+ email + "' non presente in archivio")) );

        return dao.save(entity);
    }

    @Override
    public DocumentaleDTO download(String uuid) {
        final DocumentaleEntity file = dao.findById(uuid).orElseThrow(() -> new EntityNotFoundException("File '" + uuid + "' non presente in archivio"));
        return new DocumentaleDTO(file.getId(),file.getNome(), file.getTipo(), file.getDati(), file.getInsertDate(), file.getUpdateDate());
    }
}
