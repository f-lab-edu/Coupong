package com.coupong.constant;

public enum BaseStatus {
    SUCCESS(200),

    BAD_REQUEST(400);


    private final int code;

    private BaseStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
