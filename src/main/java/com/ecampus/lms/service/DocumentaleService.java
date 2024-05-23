package com.ecampus.lms.service;

import com.ecampus.lms.dto.response.DocumentaleDTO;
import com.ecampus.lms.entity.DocumentaleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentaleService {
    DocumentaleEntity store(MultipartFile file) throws IOException;
    DocumentaleDTO download(String uuid);
}
