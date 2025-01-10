package com.example.to_do_server.user.domain.dto;

import com.example.to_do_server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProfileDto {
    private String userId;
    private String name;
    private String email;
    private LocalDate birth;

    public ProfileDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birth = user.getBirth();
    }
}
