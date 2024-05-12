package com.ecampus.lms.security;

import com.ecampus.lms.enums.UserRole;

public record SecurityContextDetails(String token, String username, UserRole role, String nome, String cognome) {
}
