package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CredentialsRequest;

public interface LoginService {

    String login(CredentialsRequest credentials);

    void logout(String token);
}
