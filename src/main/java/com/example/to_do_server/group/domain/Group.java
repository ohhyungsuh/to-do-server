package com.example.to_do_server.group.domain;

import com.example.to_do_server.global.entity.BaseTimeEntity;
import com.example.to_do_server.user.domain.User;
import com.example.to_do_server.user_group.domain.UserGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "groups")
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<UserGroup> userGroups = new ArrayList<>();

}