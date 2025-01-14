package com.example.to_do_server.user_group.presentation;

import com.example.to_do_server.user_group.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserGroupController {

    private final UserGroupService userGroupService;

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
