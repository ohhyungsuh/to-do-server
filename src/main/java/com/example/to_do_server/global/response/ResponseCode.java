package com.example.to_do_server.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // 성공
    REQUEST_OK(HttpStatus.OK, "올바른 요청입니다."),

    // 인증, 인가
    USER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "해당 작업을 수행할 권한이 없습니다."),
    USER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "로그인하지 않은 사용자입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다."),
    NOT_EXIST_SESSION(HttpStatus.UNAUTHORIZED, "세션이 존재하지 않습니다."),
    INVALID_SESSION(HttpStatus.UNAUTHORIZED, "올바르지 않은 세션입니다."),

    // 회원가입 에러
    EXIST_USER_ID(HttpStatus.CONFLICT, "사용중인 아이디입니다."),
    EXIST_USER_EMAIL(HttpStatus.CONFLICT, "사용중인 이메일입니다."),
    EXIST_USER_NAME(HttpStatus.CONFLICT, "사용중인 이름입니다."),

    // 로그인 에러
    INCORRECT_LOGIN_ID(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),

    // 그룹 에러
    NOT_EXIST_GROUP(HttpStatus.NOT_FOUND, "해당 그룹을 찾을 수 없습니다."),
    DENY_GROUP(HttpStatus.FORBIDDEN, "추방당한 방은 들어갈 수 없습니다."),

    // 유저-그룹 에러
    EXIST_USER_GROUP(HttpStatus.CONFLICT, "이미 참가 요청을 보냈거나, 참여중인 그룹입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
