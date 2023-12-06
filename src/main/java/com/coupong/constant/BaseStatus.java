package com.coupong.constant;

public enum BaseStatus {
    SUCCESS(200),

    BAD_REQUEST(400),

    // Order Bad Request 45x
    NO_ITEM_TO_ORDER(450),
    UNORDERABLE_ITEM(451),
    OUT_OF_STOCK(452),

    // Coupon Bad Request 47x
    UNAVAILABLE_COUPON(470),
    BEFORE_COUPON_START_TIME(471),
    AFTER_COUPON_END_TIME(472),
    COUPON_USED(473),
    COUPON_HAS_RUN_OUT(474),
    NOT_FOUND(475),
    HAVE_TO_BASKET_COUPON(476),
    HAVE_TO_ITEM_COUPON(477)

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