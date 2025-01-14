package com.example.to_do_server.user_group.service;

import com.example.to_do_server.group.domain.repository.GroupRepository;
import com.example.to_do_server.user.domain.repository.UserRepository;
import com.example.to_do_server.user_group.domain.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    // todo 그룹 가입 요청

    // todo 그룹 가입 요청 삭제

    // todo 내가 참여 중인, 참여 대기 중인 그룹 조회(API 나눠도 됨)

    // todo 그룹 내 인원 조회

    // todo 그룹 나가기



    // todo 그룹 가입 요청 수락

    // todo 그룹 가입 요청 거절

    // todo 그룹 가입 요청 조회

    // todo 그룹 내 인원 추방
}
