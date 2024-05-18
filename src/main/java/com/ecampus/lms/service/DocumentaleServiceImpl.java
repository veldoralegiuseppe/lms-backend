package com.ecampus.lms.service;

import com.ecampus.lms.dao.DocumentaleDAO;
import com.ecampus.lms.entity.DocumentaleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentaleServiceImpl implements DocumentaleService{

    private final DocumentaleDAO dao;

    public void store(MultipartFile file) throws IOException {
        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final String contentType = file.getContentType();
        final byte[] data = file.getBytes();

        DocumentaleEntity entity = new DocumentaleEntity();
        entity.setNome(fileName);
        entity.setTipo(contentType);
        entity.setDati(data);

        dao.save(entity);
    }
}
