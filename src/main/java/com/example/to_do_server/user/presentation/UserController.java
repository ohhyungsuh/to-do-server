package com.example.to_do_server.user.presentation;

import com.example.to_do_server.global.response.BaseResponse;
import com.example.to_do_server.global.response.ResponseCode;
import com.example.to_do_server.session.SessionUtils;
import com.example.to_do_server.user.domain.dto.LoginDto;
import com.example.to_do_server.user.domain.dto.ProfileDto;
import com.example.to_do_server.user.domain.dto.SignupDto;
import com.example.to_do_server.user.domain.dto.UserDto;
import com.example.to_do_server.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public BaseResponse<UserDto> signup(@RequestBody @Valid SignupDto signupDto) {
        UserDto userDto = userService.signup(signupDto);
        return new BaseResponse<>(userDto);
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse<UserDto> login(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request) {
        UserDto userDto = userService.login(loginDto, request);
        return new BaseResponse<>(userDto);
    }

    // 로그아웃
    @PostMapping("/logout")
    public BaseResponse<?> logout(HttpServletRequest request) {
        userService.logout(request);
        return new BaseResponse<>(ResponseCode.REQUEST_OK);
    }

    // 회원 탈퇴
    @DeleteMapping
    public BaseResponse<?> remove(HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        userService.remove(userId);
        return new BaseResponse<>(ResponseCode.REQUEST_OK);
    }

    // 내 프로필 조회
    @GetMapping("/me/profile")
    public BaseResponse<ProfileDto> getProfile(HttpServletRequest request) {
        Long userId = SessionUtils.getUserIdBySession(request);
        ProfileDto profileDto = userService.getProfile(userId);
        return new BaseResponse<>(profileDto);
    }
}
