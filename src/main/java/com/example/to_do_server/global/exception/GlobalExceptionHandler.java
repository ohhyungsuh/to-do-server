package com.example.to_do_server.global.exception;

import com.example.to_do_server.global.response.BaseResponse;
import com.example.to_do_server.global.response.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 모든 컨트롤러에서 발생하는 예외를 처리하도록 설계된 클래스를 이를 명시적으로 나타내기 위해
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<BaseResponse<Object>> handleGlobalException(GlobalException exception) {
        logger.error("GlobalException occurred: {}", exception.getMessage(), exception);
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BaseResponse.error(errorCode));
    }
}
