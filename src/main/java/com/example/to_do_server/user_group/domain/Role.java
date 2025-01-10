package com.example.to_do_server.user_group.domain;

import lombok.Getter;

@Getter
public enum Role {
    // 그룹 수정, 삭제, 인원 관리(수락, 거절, 추방)
    OWNER,

    // 인원 관리(수락, 거절, 추방)
    MANAGER,

    // 일반
    MEMBER;
}
