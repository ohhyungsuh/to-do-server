package com.example.to_do_server.user_group.presentation;

import com.example.to_do_server.global.response.BaseResponse;
import com.example.to_do_server.global.response.ResponseCode;
import com.example.to_do_server.session.SessionUtils;
import com.example.to_do_server.user_group.domain.dto.UserInfoDto;
import com.example.to_do_server.user_group.service.UserGroupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user-group")
public class UserGroupController {

    private final UserGroupService userGroupService;

    // todo 알림 테이블 만들어서 실시간 알람 기능 만들어보기
    // 그룹 가입 요청
    @PostMapping(value = "/{groupId}")
    public BaseResponse<?> sendJoin(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        userGroupService.sendJoin(userId, groupId);
        return new BaseResponse<>(ResponseCode.REQUEST_OK);
    }

    // 그룹 가입 요청 삭제
    @DeleteMapping(value = "/{groupId}/remove")
    public BaseResponse<?> deleteJoin(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        userGroupService.deleteJoin(userId, groupId);
        return new BaseResponse<>(ResponseCode.REQUEST_OK);
    }

    // 그룹 내 인원 조회
    @GetMapping(value = "/{groupId}/users")
    public BaseResponse<UserInfoDto> getUserInfoInGroup(@PathVariable Long groupId, HttpServletRequest request) {
        SessionUtils.getUserIdBySession(request);
        List<UserInfoDto> userInfos = userGroupService.getUserInfoInGroup(groupId);
        return new BaseResponse<>(userInfos);
    }

    // 그룹 나가기
    @DeleteMapping(value = "/{groupId}/leave")
    public BaseResponse<?> leaveGroup(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        userGroupService.deleteJoin(userId, groupId);
        return new BaseResponse<>(ResponseCode.REQUEST_OK);
    }



    // todo 그룹 가입 요청 수락

    // todo 그룹 가입 요청 거절

    // todo 그룹 가입 요청 조회

    // todo 그룹 내 인원 추방
}
