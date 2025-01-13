package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.group.domain.Group;
import lombok.Getter;

@Getter
public class GroupInfoDto {
    private final Long id;
    private final String name;
    private final String description;

    public GroupInfoDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
    }

}
