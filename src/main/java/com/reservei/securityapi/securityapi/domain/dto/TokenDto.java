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

    String token;

    public static TokenDto toDto(String token) {
        return new TokenDto(token);
    }

}
