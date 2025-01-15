package com.example.to_do_server.user_group.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ResponseCode;
import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.group.domain.repository.GroupRepository;
import com.example.to_do_server.user.domain.User;
import com.example.to_do_server.user.domain.repository.UserRepository;
import com.example.to_do_server.user_group.domain.Role;
import com.example.to_do_server.user_group.domain.Status;
import com.example.to_do_server.user_group.domain.UserGroup;
import com.example.to_do_server.user_group.domain.dto.UserGroupDto;
import com.example.to_do_server.user_group.domain.dto.UserInfoDto;
import com.example.to_do_server.user_group.domain.repository.UserGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    // 그룹 가입 요청
    @Transactional
    public void sendJoin(Long userId, Long groupId) {
        Optional<UserGroup> findUserGroup = userGroupRepository.findByUserIdAndGroupId(userId, groupId);

        if (findUserGroup.isPresent()) {
            throw GlobalException.from(ResponseCode.EXIST_USER_GROUP);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> GlobalException.from(ResponseCode.INVALID_SESSION));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> GlobalException.from(ResponseCode.NOT_EXIST_GROUP));

        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .status(Status.PENDING)
                .build();

        userGroupRepository.save(userGroup);
    }

    // 그룹 가입 요청 삭제
    @Transactional
    public void deleteJoin(Long userId, Long groupId) {
        UserGroup userGroup = getUserGroupByUserIdAndGroupId(userId, groupId);

        userGroupRepository.deleteById(userGroup.getId());
    }

    // 그룹 내 인원 조회
    public List<UserInfoDto> getUserInfoInGroup(Long groupId) {
        return userGroupRepository.findByGroupId(groupId).stream()
                .filter(userGroup -> userGroup.getStatus().equals(Status.JOIN))
                .map(userGroup -> new UserInfoDto(userGroup.getUser()))
                .collect(Collectors.toList());
    }

    // 그룹 나가기
    @Transactional
    public void leaveGroup(Long userId, Long groupId) {
        UserGroup userGroup = getUserGroupByUserIdAndGroupId(userId, groupId);

        userGroupRepository.deleteById(userGroup.getId());
    }

    // 그룹 가입 요청 조회
    public List<UserGroupDto> getRequestUsers(Long userId, Long groupId) {
        isGroupAuthorized(userId, groupId);

        return userGroupRepository.findByGroupId(groupId).stream()
                .filter(userGroup -> userGroup.getStatus().equals(Status.PENDING))
                .map(userGroup -> new UserGroupDto(userGroup.getUser()))
                .toList();
    }

    // 그룹 가입 요청 수락
    @Transactional
    public void acceptRequestUser(Long adminUserId, Long userId, Long groupId) {
        isGroupAuthorized(adminUserId, groupId);

        getUserGroupByUserIdAndGroupId(userId, groupId).acceptUser();
    }

    // 그룹 가입 요청 거절
    @Transactional
    public void denyRequestUser(Long adminUserId, Long userId, Long groupId) {
        isGroupAuthorized(adminUserId, groupId);

        UserGroup userGroup = getUserGroupByUserIdAndGroupId(userId, groupId);

        userGroupRepository.delete(userGroup);
    }

    // 그룹 내 인원 추방
    public void expelUser(Long adminUserId, Long userId, Long groupId) {
        isGroupAuthorized(adminUserId, groupId);

        UserGroup userGroup = getUserGroupByUserIdAndGroupId(userId, groupId);

        userGroup.expelUser();
    }

    private UserGroup getUserGroupByUserIdAndGroupId(Long userId, Long groupId) {
        return userGroupRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> GlobalException.from(ResponseCode.INVALID_REQUEST));
    }

    private void isGroupAuthorized(Long userId, Long groupId) {
        if(getUserGroupByUserIdAndGroupId(userId, groupId).getRole().equals(Role.MANAGER)) {
            throw GlobalException.from(ResponseCode.USER_NOT_AUTHORIZED);
        }
    }

}
