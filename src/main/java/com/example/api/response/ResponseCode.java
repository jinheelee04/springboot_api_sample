package com.example.api.response;

public enum ResponseCode {
    SUCCESS(0),
    FAIL(1),
    ;

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
