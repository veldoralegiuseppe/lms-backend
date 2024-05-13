package com.ecampus.lms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecampus.lms.enums.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    @Value("${config.app.jwt.secret_key}")
    private String secretKey;

    @Value("${config.app.jwt.token_prefix}")
    @Getter
    private String tokenPrefix;

    @Value("${config.app.jwt.token_expiration_after_days}")
    private Integer tokenExpirationAfterDays;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public String buildJwt(final String username,
                           final UserRole role,
                           final String nome,
                           final String cognome){
        return JWT.create()
                .withSubject(username)
                .withClaim("ruolo", role.name())
                .withClaim("nome", nome)
                .withClaim("cognome", cognome)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(tokenExpirationAfterDays)))
                .sign(algorithm);
    }

    public DecodedJWT verify(final String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
