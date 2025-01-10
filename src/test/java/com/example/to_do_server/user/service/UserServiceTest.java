package com.example.to_do_server.user.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.user.domain.dto.LoginDto;
import com.example.to_do_server.user.domain.dto.SignupDto;
import com.example.to_do_server.user.domain.dto.UserDto;
import com.example.to_do_server.user.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
@DisplayName("User Test")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Mock
    HttpServletRequest request;  // Mock 객체 주입

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Mock 초기화
    }

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

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인_성공_테스트() {
        // given
        SignupDto signupDto = new SignupDto("userId1", "1234", "user1", "user1@test.com", LocalDate.of(2000, 1, 1));
        userService.signup(signupDto);

        // when
        LoginDto loginDto = new LoginDto("userId1", "1234");
        UserDto userDto = userService.login(loginDto, request);

        // then
        assertThat(userDto.getName()).isEqualTo(signupDto.getName());

    }

    @Test
    @DisplayName("아이디 틀린 경우 테스트")
    void 아이디_불일치_테스트() {
        // 일치하는 아이디가 없는 경우 에러를 발생해야 한다.
    }

    @Test
    @DisplayName("비밀번호 틀린 경우 테스트")
    void 비밀번호_불일치_테스트() {
        // 비밀번호를 잘못 입력 시 에러가 발생해야 한다.
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void 로그아웃_테스트() {
        // 로그아웃 후 user 객체를 찾으면 에러가 발생해야 한다.
    }


}