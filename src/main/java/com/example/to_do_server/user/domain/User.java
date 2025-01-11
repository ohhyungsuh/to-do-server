package com.example.to_do_server.user.domain;

import com.example.to_do_server.global.entity.BaseTimeEntity;
import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.user_group.domain.UserGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String name;

    @Column
    private String email;

    @Column
    private LocalDate birth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserGroup> userGroups = new ArrayList<>();
}
