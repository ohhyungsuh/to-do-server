package com.example.to_do_server.global.exception;

import com.example.to_do_server.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    private GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static GlobalException from(ErrorCode errorCode) {
        return new GlobalException(errorCode);
    }
}
