package com.ecampus.lms.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.ecampus.lms.enums.UserRole).ADMIN.name()) " +
              "or hasAuthority(T(com.ecampus.lms.enums.UserRole).STUDENTE.name())" +
              "or hasAuthority(T(com.ecampus.lms.enums.UserRole).DOCENTE.name())")
public @interface PreAuthorizeAll {
}
