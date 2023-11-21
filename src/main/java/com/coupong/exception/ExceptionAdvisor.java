package com.coupong.exception;

import com.coupong.config.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> resolveException(BadRequestException ex){
        BaseResponse baseResponse = ex.getBaseResponse();
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }
}
