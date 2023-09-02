package com.reservei.securityapi.securityapi.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reservei.securityapi.securityapi.domain.model.User;
import com.reservei.securityapi.securityapi.exception.GenericException;
import com.reservei.securityapi.securityapi.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    UserRepository userRepository;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("reservei-security-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro na geração do token", ex);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("reservei-security-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "invalid";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String refreshToken(String token, String login) throws GenericException {
        if(!isValidSubject(token, login)) {
            throw new GenericException("Usuário não tem permissão para refresh desse token");
        }
        User user = (User) userRepository.findByLogin(login);
        if(user != null) {
            return generateToken(user);
        } else {
            throw new GenericException("Usuário não encontrado para o login informado");
        }
    }

    public Boolean isJWTExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }

    public String getExpiration(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return fmt.format(decodedJWT.getExpiresAt());
    }

    private Boolean isValidSubject(String token, String login) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String subject = decodedJWT.getSubject();
        return subject.equals(login);
    }
}
