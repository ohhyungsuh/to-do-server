package com.example.to_do_server.group.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenerateGroupDto {
    @NotBlank
    private String name;

    private String description;

    public GenerateGroupDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
