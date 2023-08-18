package com.reservei.securityapi.securityapi.controller;

import com.reservei.securityapi.securityapi.config.security.TokenService;
import com.reservei.securityapi.securityapi.domain.dto.TokenDto;
import com.reservei.securityapi.securityapi.domain.model.User;
import com.reservei.securityapi.securityapi.domain.record.AuthenticationData;
import com.reservei.securityapi.securityapi.domain.record.TokenData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationData data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(TokenDto.toDto(token));
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestBody TokenData data) {
        try {
            String user = tokenService.validateToken(data.token());
            return !user.equals("invalid");
        } catch (Exception ex) {
            throw new RuntimeException("Erro");
        }
    }
}
