package com.reservei.securityapi.securityapi.controller;

import com.reservei.securityapi.securityapi.config.security.TokenService;
import com.reservei.securityapi.securityapi.domain.dto.MessageDto;
import com.reservei.securityapi.securityapi.domain.dto.UserDto;
import com.reservei.securityapi.securityapi.domain.record.TokenData;
import com.reservei.securityapi.securityapi.domain.record.UserData;
import com.reservei.securityapi.securityapi.exception.GenericException;
import com.reservei.securityapi.securityapi.repository.UserRepository;
import com.reservei.securityapi.securityapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<UserDto> create(@RequestBody UserData data, UriComponentsBuilder uriBuilder) throws GenericException {
        UserDto dto = userService.create(data);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) throws Exception {
        UserDto dto = userService.findById(id);

        return ResponseEntity.ok().body(dto);
    }


    @PostMapping("/validate")
    public String validateToken(@RequestBody TokenData data) {
        try {
            return tokenService.validateToken(data.token());
        } catch (Exception ex) {
            throw new RuntimeException("Erro");
        }
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<UserDto> updateByPublicId(@PathVariable String publicId,
                                                    @RequestBody UserData data,
                                                    @RequestHeader("Authorization") String token) {
        UserDto dto = userService.updateByPublicId(publicId, data);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("reactivate/{publicId}")
    public ResponseEntity<MessageDto> reactivateByPublicId(@PathVariable String publicId,
                                                           @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = userService.reactivateById(publicId);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<MessageDto> deleteByPublicId(@PathVariable String publicId,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        MessageDto dto = userService.deleteById(publicId);

        return ResponseEntity.ok().body(dto);
    }
}
