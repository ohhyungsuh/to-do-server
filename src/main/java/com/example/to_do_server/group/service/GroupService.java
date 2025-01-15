package com.example.to_do_server.group.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ResponseCode;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Group group = groupRepository.save(Group.createGroup(generateGroupDto));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> GlobalException.from(ResponseCode.INVALID_SESSION));

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

    // 나와 관련된 모든 그룹 조회
    public List<GroupInfoDto> getMyGroupInfos(Long userId) {
        List<UserGroup> userGroups = userGroupRepository.findByUserId(userId);

        return userGroups.stream()
                .map(userGroup -> new GroupInfoDto(userGroup.getGroup(), userGroup.getStatus()))
                .toList();
    }

    // joined 또는 pending 그룹 조회
    public List<GroupInfoDto> getMyGroupInfosByStatus(Long userId, Status status) {
        List<UserGroup> userGroups = userGroupRepository.findByUserId(userId);

        return userGroups.stream()
                .filter(userGroup -> userGroup.getStatus().equals(status))
                .map(userGroup -> new GroupInfoDto(userGroup.getGroup(), userGroup.getStatus()))
                .toList();
    }

    // 그룹 목록 전체 조회
    public List<GroupInfoDto> getGroupInfos(Long userId) {
        return groupRepository.findAll().stream()
                .map(group -> {
                    Status status = userGroupRepository.findByUserIdAndGroupId(userId, group.getId())
                            .map(UserGroup::getStatus)
                            .orElse(Status.NOT_JOIN);
                    return new GroupInfoDto(group, status);
                })
                .toList();
    }

    // 그룹 한 개 조회
    public GroupDetailDto getGroupDetail(Long userId, Long groupId) {
        // groupId를 가진 데이터 담기
        log.info("findByGroupId 쿼리 실행");
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> GlobalException.from(ResponseCode.NOT_EXIST_GROUP));

        List<UserGroup> userGroups = userGroupRepository.findByGroupId(groupId);

        GroupInfoDto groupInfoDto = new GroupInfoDto(group, getStatus(userGroups, userId));
        GroupOwnerDto ownerDto = getGroupOwner(userGroups);

        return GroupDetailDto.builder()
                .groupInfoDto(groupInfoDto)
                .groupOwnerDto(ownerDto)
                .build();
    }

    // 그룹 삭제
    @Transactional
    public void remove(Long userId, Long groupId) {
        UserGroup userGroup = userGroupRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> GlobalException.from(ResponseCode.INVALID_REQUEST));

        if (!userGroup.getRole().equals(Role.OWNER)) {
            throw GlobalException.from(ResponseCode.USER_NOT_AUTHORIZED);
        }

        groupRepository.deleteById(groupId);
    }

    private GroupOwnerDto getGroupOwner(List<UserGroup> userGroups) {
        // OWNER가 없다는 뜻은 그룹이 삭제됐다는 뜻
        User user = userGroups.stream()
                .filter(userGroup -> userGroup.getRole().equals(Role.OWNER))
                .map(UserGroup::getUser)
                .findFirst()
                .orElseThrow(() -> GlobalException.from(ResponseCode.NOT_EXIST_GROUP));

        return new GroupOwnerDto(user);
    }


    private Status getStatus(List<UserGroup> userGroups, Long userId) {
        return userGroups.stream()
                .filter(userGroup -> userGroup.getUser().getId().equals(userId))
                .map(UserGroup::getStatus)
                .findFirst()
                .orElse(Status.NOT_JOIN);
    }
}
