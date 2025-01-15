package com.example.to_do_server.global.response;

import com.example.to_do_server.global.exception.GlobalException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class BaseResponse<T> {
    private final Status status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Metadata metadata; // 결과 개수

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<T> results;

    // 단일 결과 반환
    public BaseResponse(T data) {
        this.status = new Status(ResponseCode.REQUEST_OK);
        this.metadata = new Metadata(1);
        this.results = List.of(data);
    }

    // 여러 개의 결과 반환
    public BaseResponse(List<T> results) {
        this.status = new Status(ResponseCode.REQUEST_OK);
        this.metadata = new Metadata(results.size());
        this.results = results;
    }

    public BaseResponse(ResponseCode responseCode) {
        this.status = new Status(responseCode);
    }

    public BaseResponse(GlobalException exception) {
        this.status = new Status(exception.getResponseCode());
    }

    @Getter
    @AllArgsConstructor
    private static class Metadata {
        private int resultCount = 0;
    }

    @Getter
    private static class Status {
        private final int code;
        private final String message;

        public Status(ResponseCode responseCode) {
            this.code = responseCode.getHttpStatus().value();
            this.message = responseCode.getMessage();
        }
    }
}
