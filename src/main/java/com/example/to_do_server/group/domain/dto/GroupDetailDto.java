package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.user_group.domain.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GroupDetailDto {
    private final Long id;
    private final String name;
    private final String description;
    private final GroupAdminsDto groupAdminsDto;
    private final Status myStatus;

    public GroupDetailDto(Group group, GroupAdminsDto adminsDto, Status myStatus) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.groupAdminsDto = adminsDto;
        this.myStatus = myStatus;
    }
}
