package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.user.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class GroupOwnerDto {
    private final Long id;
    private final String name;

    public GroupOwnerDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
