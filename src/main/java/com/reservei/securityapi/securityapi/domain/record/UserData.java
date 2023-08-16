package com.reservei.securityapi.securityapi.domain.record;

import com.reservei.securityapi.securityapi.domain.model.UserRole;

public record UserData(

        String publicId,
        String email,
        String password,
        UserRole role) {
}
