package com.example.to_do_server.group.presentation;

import com.example.to_do_server.global.response.BaseResponse;
import com.example.to_do_server.group.domain.dto.GenerateGroupDto;
import com.example.to_do_server.group.domain.dto.GroupDetailDto;
import com.example.to_do_server.group.domain.dto.GroupDto;
import com.example.to_do_server.group.domain.dto.GroupInfoDto;
import com.example.to_do_server.group.service.GroupService;
import com.example.to_do_server.session.SessionUtils;
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
    public ResponseEntity<BaseResponse<GroupDto>> generate(@RequestBody @Valid GenerateGroupDto generateGroupDto, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        GroupDto groupDto = groupService.generate(generateGroupDto, userId);
        return ResponseEntity.ok(BaseResponse.success("그룹이 생성됐습니다.", groupDto));
    }

    // 그룹 삭제
    @DeleteMapping(path = "/{groupId}")
    public ResponseEntity<BaseResponse<?>> remove(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        groupService.remove(userId, groupId);
        return ResponseEntity.ok(BaseResponse.success("그룹이 삭제되었습니다."));
    }

    // 내 그룹 조회
    @GetMapping(path = "/me")
    public ResponseEntity<BaseResponse<List<GroupInfoDto>>> getMyGroupInfos(HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        List<GroupInfoDto> groupInfos = groupService.getMyGroupInfos(userId);
        return ResponseEntity.ok(BaseResponse.success("내 그룹을 조회했습니다.", groupInfos));
    }

    // 그룹 한 개 조회
    @GetMapping(path = "/{groupId}")
    public ResponseEntity<BaseResponse<GroupDetailDto>> getGroupDetail(@PathVariable Long groupId, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        GroupDetailDto groupDetailDto = groupService.getGroupDetail(userId, groupId);
        return ResponseEntity.ok(BaseResponse.success("그룹 한개를 조회했습니다.", groupDetailDto));
    }

    // 전체 그룹 조회
    @GetMapping
    public ResponseEntity<BaseResponse<List<GroupInfoDto>>> getGroupInfos(HttpServletRequest request) {
        SessionUtils.getUserIdBySession(request);
        List<GroupInfoDto> groupInfos = groupService.getGroupInfos();
        return ResponseEntity.ok(BaseResponse.success("그룹을 조회했습니다.", groupInfos));
    }
}
