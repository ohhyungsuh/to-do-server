package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;

    public GroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
    }
}
