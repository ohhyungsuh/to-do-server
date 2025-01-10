package com.example.to_do_server.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    public LoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
