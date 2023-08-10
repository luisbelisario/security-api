package com.reservei.securityapi.securityapi.domain.record;

public record UserData(

        String public_id,
        String email,
        String password,
        String role) {
}
