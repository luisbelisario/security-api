package com.reservei.securityapi.securityapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenDto {

    String publicId;
    String email;
    String role;
    String token;
    String expiresAt;

    public static TokenDto toDto(String publicId, String email, String role, String token, String expiresAt) {
        return new TokenDto(publicId, email, role, token, expiresAt);
    }

}
