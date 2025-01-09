package com.example.to_do_server.user.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.user.domain.dto.SignupDto;
import com.example.to_do_server.user.domain.dto.UserDto;
import com.example.to_do_server.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@DisplayName("User Test")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private SignupDto createUserDto(String userId, String password, String name, String email, LocalDate birth) {
        return new SignupDto(userId, password, name, email, birth);
    }

    /*
     * 회원가입 테스트
     */
    @Test
    @DisplayName("회원가입 디비 저장 테스트")
    void 회원가입() {
        // given
        SignupDto signupDto = createUserDto("userId", "1234", "user1", "user1@test.com", LocalDate.of(2000, 1, 1));

        // when
        UserDto userDto = userService.signup(signupDto);

        // then
        assertThat(userDto.getName()).isEqualTo("user1");
    }

    @Test
    @DisplayName("회원가입 중복 아이디 테스트")
    void 중복_아이디_테스트() {
        // given
        SignupDto signupDto1 = createUserDto("userId1", "1234", "user1", "user1@test.com", LocalDate.of(2000, 1, 1));
        SignupDto signupDto2 = createUserDto("userId1", "1234", "user2", "user2@test.com", LocalDate.of(2000, 1, 1));

        // when
        userService.signup(signupDto1);

        GlobalException e = assertThrows(GlobalException.class, () -> userService.signup(signupDto2));

        // then
        assertThat(e.getMessage()).isEqualTo("사용중인 아이디입니다.");
    }

    @Test
    @DisplayName("회원가입 중복 이름 테스트")
    void 중복_이름_테스트() {
        // given
        SignupDto signupDto1 = new SignupDto("userId1", "1234", "user1", "user1@test.com", LocalDate.of(2000, 1, 1));
        SignupDto signupDto2 = new SignupDto("userId2", "1234", "user1", "user2@test.com", LocalDate.of(2000, 1, 1));

        // when
        userService.signup(signupDto1);

        GlobalException e = assertThrows(GlobalException.class, () -> userService.signup(signupDto2));

        // then
        assertThat(e.getMessage()).isEqualTo("사용중인 이름입니다.");
    }

    @Test
    @DisplayName("회원가입 중복 이메일 테스트")
    void 중복_이메일_테스트() {
        // given
        SignupDto signupDto1 = new SignupDto("userId1", "1234", "user1", "user1@test.com", LocalDate.of(2000, 1, 1));
        SignupDto signupDto2 = new SignupDto("userId2", "1234", "user2", "user1@test.com", LocalDate.of(2000, 1, 1));

        // when
        userService.signup(signupDto1);

        GlobalException e = assertThrows(GlobalException.class, () -> userService.signup(signupDto2));

        // then
        assertThat(e.getMessage()).isEqualTo("사용중인 이메일입니다.");

        /*
            assertThatThrownBy(() -> 객체 생성)
                .isInstanceOf(IllegalArgumentException.class);
         */
    }


}