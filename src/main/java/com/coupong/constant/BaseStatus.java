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
    ACCESS_TOKEN_OUT_DATED(416),

    // Order Bad Request 45x


    // Coupon Bad Request 47x
    UNAVAILABLE_COUPON(470),
    BEFORE_COUPON_START_TIME(471),
    AFTER_COUPON_END_TIME(472),
    COUPON_USED(473),
    COUPON_HAS_RUN_OUT(474)

    // Order Server Exception 55x


    // Coupon Server Exception 57x

    ;

    private final int code;

    private BaseStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
