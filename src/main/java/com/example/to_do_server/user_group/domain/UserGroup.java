package com.example.to_do_server.user_group.domain;

import com.example.to_do_server.global.entity.BaseTimeEntity;
import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user_group")
public class UserGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;

    private Boolean status;

}
