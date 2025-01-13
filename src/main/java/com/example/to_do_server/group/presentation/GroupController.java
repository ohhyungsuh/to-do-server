package com.example.to_do_server.group.presentation;

import com.example.to_do_server.global.response.BaseResponse;
import com.example.to_do_server.group.domain.dto.GenerateGroupDto;
import com.example.to_do_server.group.domain.dto.GroupDto;
import com.example.to_do_server.group.service.GroupService;
import com.example.to_do_server.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<BaseResponse<GroupDto>> generate(@RequestBody @Valid GenerateGroupDto generateGroupDto, HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        GroupDto groupDto = groupService.generate(generateGroupDto, userId);
        return ResponseEntity.ok(BaseResponse.success("그룹이 생성됐습니다.", groupDto));
    }
}
