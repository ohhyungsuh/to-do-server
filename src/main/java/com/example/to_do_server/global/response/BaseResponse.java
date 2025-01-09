package com.example.to_do_server.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    private final int code;
    private final String message;
    private final T result;
    private final Object meta;

    private static final int SUCCESS_CODE = 200;

    public static <T> BaseResponse<T> success(String message) {
        return BaseResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> success(String message, T result) {
        return BaseResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(message)
                .result(result)
                .build();
    }

    public static <T> BaseResponse<T> success(String message, T result, Object meta) {
        return BaseResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(message)
                .result(result)
                .meta(meta)
                .build();
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return BaseResponse.<T>builder()
                .code(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}
