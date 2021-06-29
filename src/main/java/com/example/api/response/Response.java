package com.example.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
public class Response<T> {
    @ApiModelProperty(value = "응답 코드 번호: 0이면 성공, 0이 아니면 실패")
    private final int code;

    @ApiModelProperty(value = "응답 메시지")
    private final String message;

    @ApiModelProperty(value = "응답 결과")
    private T result;

    public Response(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> Response<T> of(int code, String message, T result) {
        return new Response<>(code, message, result);
    }
}
