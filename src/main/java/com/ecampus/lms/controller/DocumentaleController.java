package com.ecampus.lms.controller;

import com.ecampus.lms.dto.response.DocumentaleDTO;
import com.ecampus.lms.dto.response.MessageDTO;
import com.ecampus.lms.security.PreAuthorizeAll;
import com.ecampus.lms.service.DocumentaleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documentale")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorizeAll
@Slf4j
public class DocumentaleController {

    private final DocumentaleService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            service.store(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            final String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageDTO(message));
        }
    }

    @PostMapping(value = "/download", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(@RequestParam("uuid") String uuid){
        final DocumentaleDTO download = service.download(uuid);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(download.data());
    }
}
