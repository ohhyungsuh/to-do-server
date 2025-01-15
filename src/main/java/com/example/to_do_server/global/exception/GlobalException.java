package com.example.to_do_server.global.exception;

import com.example.to_do_server.global.response.ResponseCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ResponseCode responseCode;

    private GlobalException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public static GlobalException from(ResponseCode responseCode) {
        return new GlobalException(responseCode);
    }
}
