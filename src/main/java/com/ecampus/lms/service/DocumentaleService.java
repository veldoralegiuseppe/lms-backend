package com.ecampus.lms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentaleService {
    void store(MultipartFile file) throws IOException;
}
