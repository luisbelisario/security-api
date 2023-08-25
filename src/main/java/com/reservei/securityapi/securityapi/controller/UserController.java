package com.reservei.securityapi.securityapi.controller;

import com.reservei.securityapi.securityapi.config.security.TokenService;
import com.reservei.securityapi.securityapi.domain.dto.MessageDto;
import com.reservei.securityapi.securityapi.domain.dto.UserDto;
import com.reservei.securityapi.securityapi.domain.model.User;
import com.reservei.securityapi.securityapi.domain.record.TokenData;
import com.reservei.securityapi.securityapi.domain.record.UserData;
import com.reservei.securityapi.securityapi.exception.GenericException;
import com.reservei.securityapi.securityapi.repository.UserRepository;
import com.reservei.securityapi.securityapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Cria um novo usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserData data, UriComponentsBuilder uriBuilder) throws GenericException {
        UserDto dto = userService.create(data);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PostMapping("/validate")
    @Operation(summary = "Valida o token de um usuário", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public String validateToken(@RequestBody TokenData data) {
        try {
            return tokenService.validateToken(data.token());
        } catch (Exception ex) {
            throw new RuntimeException("Erro");
        }
    }

    @PutMapping("/{publicId}")
    @Operation(summary = "Atualiza um usuário pelo publicId", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<UserDto> updateByPublicId(@PathVariable String publicId,
                                                    @RequestBody UserData data,
                                                    @RequestHeader("Authorization") String token) {
        UserDto dto = userService.updateByPublicId(publicId, data);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("reactivate/{publicId}")
    @Operation(summary = "Reativa um usuário pelo publicId", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> reactivateByPublicId(@PathVariable String publicId,
                                                           @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = userService.reactivateById(publicId);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{publicId}")
    @Operation(summary = "Desativa um usuário pelo publicId", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<MessageDto> deleteByPublicId(@PathVariable String publicId,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = userService.deleteById(publicId);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/healthCheck")
    @Operation(summary = "Health check da aplicação", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "Erro do servidor")
    })
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
