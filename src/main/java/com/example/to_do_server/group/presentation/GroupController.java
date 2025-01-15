package com.example.to_do_server.group.presentation;

import com.example.to_do_server.global.response.BaseResponse;
import com.example.to_do_server.global.response.ResponseCode;
import com.example.to_do_server.group.domain.dto.GenerateGroupDto;
import com.example.to_do_server.group.domain.dto.GroupDetailDto;
import com.example.to_do_server.group.domain.dto.GroupDto;
import com.example.to_do_server.group.domain.dto.GroupInfoDto;
import com.example.to_do_server.group.service.GroupService;
import com.example.to_do_server.session.SessionUtils;
import com.example.to_do_server.user_group.domain.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    // 그룹 생성
    @PostMapping
    public BaseResponse<GroupDto> generate(@RequestBody @Valid GenerateGroupDto generateGroupDto, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        GroupDto groupDto = groupService.generate(generateGroupDto, userId);
        return new BaseResponse<>(groupDto);
    }

    // 그룹 삭제
    @DeleteMapping(path = "/{groupId}")
    public BaseResponse<?> remove(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        groupService.remove(userId, groupId);
        return new BaseResponse<>(ResponseCode.REQUEST_OK);
    }

    // 내 그룹 전체 조회
    @GetMapping(path = "/me")
    public BaseResponse<GroupInfoDto> getMyGroupInfos(HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        List<GroupInfoDto> groupInfos = groupService.getMyGroupInfos(userId);
        return new BaseResponse<>(groupInfos);
    }

    // 참여중인 내 그룹 조회
    @GetMapping(path = "/me/joined")
    public BaseResponse<GroupInfoDto> getJoinedGroups(HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        List<GroupInfoDto> joinedGroupInfos = groupService.getMyGroupInfosByStatus(userId, Status.JOIN);
        return new BaseResponse<>(joinedGroupInfos);
    }

    // 대기중인 내 그룹 조회
    @GetMapping(path = "/me/pending")
    public BaseResponse<GroupInfoDto> getPendingGroups(HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        List<GroupInfoDto> pendingGroupInfos = groupService.getMyGroupInfosByStatus(userId, Status.PENDING);
        return new BaseResponse<>(pendingGroupInfos);
    }


    // 그룹 한 개 조회
    @GetMapping(path = "/{groupId}")
    public BaseResponse<GroupDetailDto> getGroupDetail(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        GroupDetailDto groupDetailDto = groupService.getGroupDetail(userId, groupId);
        return new BaseResponse<>(groupDetailDto);
    }

    // 전체 그룹 조회
    @GetMapping
    public BaseResponse<GroupInfoDto> getGroupInfos(HttpServletRequest request) {
        SessionUtils.getUserIdBySession(request);
        List<GroupInfoDto> groupInfos = groupService.getGroupInfos();
        return new BaseResponse<>(groupInfos);
    }
}
