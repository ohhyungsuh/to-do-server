package com.example.to_do_server.user_group.domain.dto;

import com.example.to_do_server.user.domain.User;
import lombok.Getter;

@Getter
public class UserGroupDto {
    private final Long id;
    private final String name;

    public UserGroupDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}