package com.example.to_do_server.user.service;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ErrorCode;
import com.example.to_do_server.session.SessionConst;
import com.example.to_do_server.user.domain.User;
import com.example.to_do_server.user.domain.dto.LoginDto;
import com.example.to_do_server.user.domain.dto.SignupDto;
import com.example.to_do_server.user.domain.dto.UserDto;
import com.example.to_do_server.user.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(SignupDto signupDto) {
        checkUserIdExists(signupDto);
        checkNameExists(signupDto);
        checkEmailExists(signupDto);

        User user = User.builder()
                .userId(signupDto.getUserId())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .birth(signupDto.getBirth())
                .build();

        userRepository.save(user);

        log.info("User saved successfully: {}", user.getId());

        return new UserDto(user);
    }

    public UserDto login(LoginDto loginDto, HttpServletRequest request) {
        Optional<User> findUser = userRepository.findByUserId(loginDto.getUserId());

        User user = findUser.orElseThrow(() -> GlobalException.from(ErrorCode.INCORRECT_LOGIN_ID));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw GlobalException.from(ErrorCode.INCORRECT_PASSWORD);
        }

        UserDto userDto = new UserDto(user);

        // 기존 세션 있으면 반환하고 없으면 생성
        HttpSession session = request.getSession();

        // session key 값 설정하고 expiration 30분으로 설정
        session.setAttribute(SessionConst.USER_ID.getKey(), user.getUserId());
        session.setMaxInactiveInterval(SessionConst.USER_ID.getExpiration());

        log.info("로그인 성공");

        return userDto;
    }

    public void logout(HttpServletRequest request) {
        // 현재 세션 또는 null 반환
        HttpSession session = request.getSession(false);

        if(session == null) {
            throw GlobalException.from(ErrorCode.NOT_EXIST_SESSION);
        }

        session.invalidate();
    }

    @Transactional
    public void remove(String userId) {
        userRepository.deleteByUserId(userId);

        log.info("회원 탈퇴");
    }

    private void checkUserIdExists(SignupDto signupDto) {
        if (userRepository.existsByUserId(signupDto.getUserId())) {
            throw GlobalException.from(ErrorCode.EXIST_USER_ID);
        }
    }

    private void checkNameExists(SignupDto signupDto) {
        if (userRepository.existsByName(signupDto.getName())) {
            throw GlobalException.from(ErrorCode.EXIST_USER_NAME);
        }
    }

    private void checkEmailExists(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            log.info("중복된 사용자 이메일");
            throw GlobalException.from(ErrorCode.EXIST_USER_EMAIL);
        }
    }

}