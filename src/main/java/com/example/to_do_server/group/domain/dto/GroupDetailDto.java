package com.example.to_do_server.group.domain.dto;

import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.user.domain.dto.UserDto;
import com.example.to_do_server.user_group.domain.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GroupDetailDto {
    @JsonProperty("group")
    private final GroupInfoDto groupInfoDto;

    @JsonProperty("user")
    private final GroupOwnerDto groupOwnerDto;
}
