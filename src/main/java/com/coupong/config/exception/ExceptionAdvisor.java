package com.coupong.config.exception;

import com.coupong.config.response.BaseResponse;
import com.coupong.constant.BaseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionAdvisor {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> resolveException(BadRequestException ex){
        BaseResponse baseResponse = ex.getBaseResponse();
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> resolveException(DataIntegrityViolationException exception){
        return new ResponseEntity<>(new BaseResponse(BaseStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
