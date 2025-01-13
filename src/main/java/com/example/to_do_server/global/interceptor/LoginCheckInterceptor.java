package com.example.to_do_server.global.interceptor;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ErrorCode;
import com.example.to_do_server.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /* todo: cors를 위한 preflight 처리 필요 */
        String requestURI = request.getRequestURI();

        log.info("로그인 체크 인터셉터 실행 {}", requestURI);

        // 1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();

        if (session == null) {
            log.info("세션이 없습니다.");
            throw GlobalException.from(ErrorCode.NOT_EXIST_SESSION);
        }

        Object userId = session.getAttribute(SessionConst.USER_ID.getKey());
        if (userId == null) {
            log.info("로그인 정보가 없습니다.");
            throw GlobalException.from(ErrorCode.INVALID_SESSION);
        }

        /* todo: redirect 필요 */
        log.info("세션 정보 확인 성공");
        return true;
    }

}
