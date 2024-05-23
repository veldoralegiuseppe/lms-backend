package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CreateAttivitaRequest;
import com.ecampus.lms.dto.response.AttivitaDTO;
import com.ecampus.lms.dto.response.AttivitaSummaryResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AttivitaService {
    AttivitaSummaryResponse getAttivitaStudente(Pageable pageable);
    AttivitaDTO create(CreateAttivitaRequest request, MultipartFile documento) throws FileUploadException;
}
