package com.example.to_do_server.session;

import com.example.to_do_server.global.exception.GlobalException;
import com.example.to_do_server.global.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SessionUtils {

    public static String getUserIdBySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Object userId = session.getAttribute(SessionConst.USER_ID.getKey());

        return userId.toString();
    }
}
