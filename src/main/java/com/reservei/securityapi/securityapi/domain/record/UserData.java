package com.reservei.securityapi.securityapi.domain.record;

import com.reservei.securityapi.securityapi.domain.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserData(

        String publicId,

        @NotBlank
        @NotEmpty
        String login,

        @NotBlank
        @NotEmpty
        String password,
        UserRole role) {
}
