package com.example.to_do_server.group.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GroupAdminsDto {
    private final Long ownerId;
    private final List<Long> managerIds;

    public GroupAdminsDto(Long ownerId, List<Long> managerIds) {
        this.ownerId = ownerId;
        this.managerIds = managerIds;
    }
}
