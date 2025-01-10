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

        if (session == null) {
            throw GlobalException.from(ErrorCode.NOT_EXIST_SESSION);
        }

        Object userId = session.getAttribute(SessionConst.USER_ID.getKey());
        if (userId == null) {
            throw GlobalException.from(ErrorCode.INVALID_SESSION);
        }

        return userId.toString();
    }
}
