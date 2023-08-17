package com.reservei.securityapi.securityapi.domain.dto;

import com.reservei.securityapi.securityapi.domain.model.User;
import com.reservei.securityapi.securityapi.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String login;
    private UserRole role;


    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setRole(user.getRole());

        return dto;
    }
}
