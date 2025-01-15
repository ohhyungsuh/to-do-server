package com.example.to_do_server.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SessionUtils {

    public static Long getUserIdBySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 따로 예외 처리할 필요 X
        return (Long) session.getAttribute(SessionConst.USER_ID.getKey());
    }
}
