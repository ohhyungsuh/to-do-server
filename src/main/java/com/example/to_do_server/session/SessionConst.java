package com.example.to_do_server.session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SessionConst {
    USER_ID("userId", 1800);

    private final String key;
    private final int expiration;
}
