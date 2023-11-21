package com.coupong.config.exception;

import com.coupong.config.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private BaseResponse baseResponse;

    public BadRequestException(BaseResponse baseResponse){
        super();
        this.baseResponse = baseResponse;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }
}
