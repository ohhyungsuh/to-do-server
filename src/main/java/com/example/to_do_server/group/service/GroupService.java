package com.example.to_do_server.group.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ErrorCode;
import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.group.domain.dto.*;
import com.example.to_do_server.group.domain.repository.GroupRepository;
import com.example.to_do_server.user.domain.User;
import com.example.to_do_server.user.domain.repository.UserRepository;
import com.example.to_do_server.user_group.domain.Role;
import com.example.to_do_server.user_group.domain.Status;
import com.example.to_do_server.user_group.domain.UserGroup;
import com.example.to_do_server.user_group.domain.repository.UserGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    /* todo 그룹 생성 개수 제한, 그룹 내 사람 명수 업데이트 */
    // 그룹 생성
    @Transactional
    public GroupDto generate(GenerateGroupDto generateGroupDto, Long userId) {
        Group group = Group.builder()
                .name(generateGroupDto.getName())
                .description(generateGroupDto.getDescription())
                .build();

        groupRepository.save(group);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> GlobalException.from(ErrorCode.INVALID_SESSION));

        // 그룹 생성과 동시에 그룹의 OWNER가 되고, 가입은 완료된 상태
        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .role(Role.OWNER)
                .status(Status.JOIN)
                .build();

        userGroupRepository.save(userGroup);

        log.info("그룹 생성 완료");
        return new GroupDto(group);
    }

    // 내 그룹 조회(참여중인 것)
    /* todo 승인 대기 목록은 UserGroupService에서 */
    public List<GroupInfoDto> getMyGroupInfos(Long userId) {
        List<UserGroup> userGroups = userGroupRepository.findByUserId(userId);

        return userGroups.stream()
                .filter(userGroup -> userGroup.getStatus().equals(Status.JOIN))
                .map(userGroup -> groupRepository.findById(userGroup.getGroup().getId()).orElse(null))
                .filter(Objects::nonNull)
                .map(GroupInfoDto::new)
                .toList();
    }

    // 그룹 목록 전체 조회
    public List<GroupInfoDto> getGroupInfos() {
        return groupRepository.findAll().stream()
                .map(GroupInfoDto::new)
                .toList();
    }

    // 그룹 한 개 조회
    public GroupDetailDto getGroupDetail(Long userId, Long groupId) {
        // groupId를 가진 데이터 담기
        log.info("findByGroupId 쿼리 실행");
        List<UserGroup> userGroups = userGroupRepository.findByGroupId(groupId);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> GlobalException.from(ErrorCode.NOT_EXIST_GROUP));

        GroupAdminsDto adminsDto = getAdmins(userGroups);
        Status myStatus = getMyStatus(userGroups, userId);

        return new GroupDetailDto(group, adminsDto, myStatus);
    }

    // 그룹 삭제
    @Transactional
    public void remove(Long userId, Long groupId) {
        UserGroup userGroup = userGroupRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> GlobalException.from(ErrorCode.NOT_EXIST_GROUP));

        if (!userGroup.getRole().equals(Role.OWNER)) {
            throw GlobalException.from(ErrorCode.USER_NOT_AUTHORIZED);
        }

        groupRepository.deleteById(groupId);
    }

    private GroupAdminsDto getAdmins(List<UserGroup> userGroups) {
        Long ownerId = null;
        List<Long> managerIds = new ArrayList<>();

        for (UserGroup userGroup : userGroups) {
            if (userGroup.getRole().equals(Role.OWNER)) {
                ownerId = userGroup.getUser().getId();
            } else if (userGroup.getRole().equals(Role.MANAGER)) {
                managerIds.add(userGroup.getUser().getId());
            }
        }

        return new GroupAdminsDto(ownerId, managerIds);
    }

    private Status getMyStatus(List<UserGroup> userGroups, Long userId) {
        return userGroups.stream()
                .filter(userGroup -> userGroup.getUser().getId().equals(userId))
                .map(UserGroup::getStatus)
                .findFirst()
                .orElse(Status.NOT_JOIN);
    }
}
