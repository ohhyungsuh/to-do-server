package com.example.to_do_server.user.service;

import com.example.to_do_server.global.config.PasswordEncoderConfig;
import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ErrorCode;
import com.example.to_do_server.user.domain.User;
import com.example.to_do_server.user.domain.dto.SignupDto;
import com.example.to_do_server.user.domain.dto.UserDto;
import com.example.to_do_server.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
