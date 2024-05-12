package com.ecampus.lms.security;

import com.ecampus.lms.enums.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        Optional.ofNullable(request.getHeader((AUTHORIZATION)))
                .filter(h -> h.startsWith(jwtService.getTokenPrefix()))
                .map(h -> h.substring(jwtService.getTokenPrefix().length()).trim())
                .map(jwtService::verify)
                .ifPresent(d -> {

                    final String username = d.getSubject();
                    final String token = request.getHeader(AUTHORIZATION).substring(jwtService.getTokenPrefix().length()).trim();

                    final String nome = d.getClaim("nome").asString();
                    final String cognome = d.getClaim("cognome").asString();
                    final UserRole ruolo = UserRole.valueOf(d.getClaim("ruolo").asString());

                    final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ruolo.name());

                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
                    usernamePasswordAuthenticationToken
                            .setDetails(new SecurityContextDetails(token, username, ruolo, nome, cognome));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                });

        filterChain.doFilter(request, response);

    }
}
