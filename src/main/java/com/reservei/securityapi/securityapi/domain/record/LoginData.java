package com.reservei.securityapi.securityapi.domain.record;

import jakarta.validation.constraints.NotBlank;

public record LoginData(

        @NotBlank
        String login) {
}
