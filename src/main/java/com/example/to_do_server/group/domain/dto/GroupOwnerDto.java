package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.user.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class GroupOwnerDto {
    private final Long ownerId;
    private final String ownerName;

    public GroupOwnerDto(User user) {
        this.ownerId = user.getId();
        this.ownerName = user.getName();
    }
}
