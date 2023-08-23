package com.reservei.securityapi.securityapi.controller;

import com.reservei.securityapi.securityapi.config.security.TokenService;
import com.reservei.securityapi.securityapi.domain.dto.TokenDto;
import com.reservei.securityapi.securityapi.domain.model.User;
import com.reservei.securityapi.securityapi.domain.record.AuthenticationData;
import com.reservei.securityapi.securityapi.domain.record.LoginData;
import com.reservei.securityapi.securityapi.domain.record.TokenData;
import com.reservei.securityapi.securityapi.exception.GenericException;
import com.reservei.securityapi.securityapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid AuthenticationData data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(TokenDto.toDto(user.getPublicId(), user.getLogin(), token));
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

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody LoginData data,
                                                 @RequestHeader("Authorization") String token) throws GenericException {
        User user = (User) userService.findByLogin(data.login());

        String refreshToken = tokenService.refreshToken(token, data.login());

        return ResponseEntity.ok(TokenDto.toDto(user.getPublicId(), user.getLogin(), refreshToken));
    }
}
