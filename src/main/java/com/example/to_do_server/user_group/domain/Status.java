package com.example.to_do_server.user_group.domain;

import lombok.Getter;

@Getter
public enum Status {
    JOIN,
    PENDING,
    NOT_JOIN,
    DENY;
}
