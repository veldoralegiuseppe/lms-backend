package com.ecampus.lms.service;

import com.ecampus.lms.dto.request.CredentialsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService{
    @Override
    public String login(CredentialsRequest credentials) {

        final String username = credentials.username();

        return null;
    }

    @Override
    public void logout(String token) {

    }
}
