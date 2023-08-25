package com.reservei.securityapi.securityapi.domain.record;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationData(

        @NotBlank
        String login,

        @NotBlank
        String password) {

}
