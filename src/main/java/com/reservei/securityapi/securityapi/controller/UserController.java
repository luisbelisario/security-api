package com.reservei.securityapi.securityapi.controller;

import com.reservei.securityapi.securityapi.domain.dto.MessageDto;
import com.reservei.securityapi.securityapi.domain.dto.UserDto;
import com.reservei.securityapi.securityapi.domain.record.UserData;
import com.reservei.securityapi.securityapi.service.UserService;
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

    @PutMapping("/{publicId}")
    public ResponseEntity<UserDto> updateByPublicId(@PathVariable String publicId, @RequestBody UserData data) {
        UserDto dto = userService.updateByPublicId(publicId, data);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{publicId}")
    public ResponseEntity<MessageDto> reactivateByPublicId(@PathVariable String publicId) throws Exception {
        MessageDto dto = userService.reactivateById(publicId);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<MessageDto> deleteByPublicId(@PathVariable String publicId) throws Exception {
        MessageDto dto = userService.deleteById(publicId);

        return ResponseEntity.ok().body(dto);
    }
}
