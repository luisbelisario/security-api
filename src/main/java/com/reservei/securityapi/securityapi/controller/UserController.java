package com.reservei.securityapi.securityapi.controller;

import com.reservei.securityapi.securityapi.domain.dto.MessageDto;
import com.reservei.securityapi.securityapi.domain.dto.UserDto;
import com.reservei.securityapi.securityapi.domain.record.UserData;
import com.reservei.securityapi.securityapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserData data, UriComponentsBuilder uriBuilder) throws Exception {
        UserDto dto = userService.create(data);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) throws Exception {
        UserDto dto = userService.findById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateById(@PathVariable Long id, @RequestBody UserData data) throws Exception {
        UserDto dto = userService.updateById(id, data);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageDto> reactivateById(@PathVariable Long id) throws Exception {
        MessageDto dto = userService.reactivateById(id);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteById(@PathVariable Long id) throws Exception {
        MessageDto dto = userService.deleteById(id);

        return ResponseEntity.ok().body(dto);
    }
}
