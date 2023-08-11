package com.reservei.securityapi.securityapi.domain.record;

public record UserData(

        String publicId,
        String email,
        String password,
        String role) {
}
