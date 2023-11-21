package com.coupong.config.response;

import com.coupong.constant.BaseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseResponse {
    private final int resCd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object data;

    // payload 데이터가 없는 경우
    public BaseResponse(BaseStatus status){
        this.resCd = status.getCode();
        this.data = null;
    }

    // payload가 있는경우
    public BaseResponse(Object data){
        this.resCd = BaseStatus.SUCCESS.getCode();
        this.data = data;
    }

    public int getResCd() {
        return resCd;
    }

    public Object getData() {
        return data;
    }
}
