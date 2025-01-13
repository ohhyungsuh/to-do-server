package com.example.to_do_server.user_group.domain.repository;

import com.example.to_do_server.user_group.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);
    List<UserGroup> findByUserId(Long userId);
    List<UserGroup> findByGroupId(Long groupId);

//    String loginId를 사용하고 싶을 때 즉시 로딩으로 연관된 엔티티를 한 번에 가져온다
//    @Query("SELECT ug FROM UserGroup ug JOIN FETCH ug.user u WHERE u.userId = :userId")
//    List<UserGroup> findByLoginId(@Param("userId") String userId);
}
