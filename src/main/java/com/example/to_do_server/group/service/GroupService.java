package com.example.to_do_server.group.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ErrorCode;
import com.example.to_do_server.group.domain.Group;
import com.example.to_do_server.group.domain.dto.GenerateGroupDto;
import com.example.to_do_server.group.domain.dto.GroupDto;
import com.example.to_do_server.group.domain.repository.GroupRepository;
import com.example.to_do_server.user.domain.User;
import com.example.to_do_server.user.domain.repository.UserRepository;
import com.example.to_do_server.user_group.domain.Role;
import com.example.to_do_server.user_group.domain.UserGroup;
import com.example.to_do_server.user_group.domain.repository.UserGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                .status(true)
                .build();

        userGroupRepository.save(userGroup);

        log.info("그룹 생성 완료");
        return new GroupDto(group);
    }

    // 그룹 조회


    // 그룹 삭제

}
