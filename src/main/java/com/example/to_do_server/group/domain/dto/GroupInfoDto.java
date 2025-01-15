package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.user_group.domain.Status;
import lombok.Getter;

@Getter
public class GroupInfoDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Status status;

    public GroupInfoDto(Group group, Status status) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.status = status;
    }

}
