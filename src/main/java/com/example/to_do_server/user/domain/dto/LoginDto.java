package com.example.to_do_server.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public LoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
