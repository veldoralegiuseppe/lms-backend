package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CreateModuloRequest;
import com.ecampus.lms.dto.response.ModuloDTO;

public interface ModuloService {
    ModuloDTO create(CreateModuloRequest request);
}
