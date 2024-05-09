package com.ecampus.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    public ResponseEntity<Void> login(){
        return ResponseEntity.ofNullable(null);
    }

    public ResponseEntity<Void> logout(){
        return ResponseEntity.ofNullable(null);
    }

}
