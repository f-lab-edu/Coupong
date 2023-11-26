package com.coupong.constant;

public enum BaseStatus {
    SUCCESS(200),

    BAD_REQUEST(400),

    CANNOT_FIND_USER(410),
    PASSWORD_MISMATCH(411),
    ACCESS_TOKEN_NOT_FOUND(412),
    REFRESH_TOKEN_NOT_FOUND(413),
    BLANK_ACCESS_TOKEN(414),
    BLANK_REFRESH_TOKEN(415),
    ACCESS_TOKEN_OUT_DATED(416);


    private final int code;

    private BaseStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
