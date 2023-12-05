package com.coupong.config.exception;

import com.coupong.config.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.coupong.constant.BaseStatus.NOT_FOUND;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends RuntimeException {

    private BaseResponse baseResponse;

    public NotFoundException(){
        super();
        this.baseResponse = new BaseResponse(NOT_FOUND);
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }
}
