package com.coupong.config.aop;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.BaseStatus;
import com.coupong.entity.Token;
import com.coupong.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginCheckAspect {
    @Value("${access-token.header-name}")
    private String atkHeader;

    @Value("${refresh-token.header-name}")
    private String rtkHeader;

    @Value("${request-header.token-name}")
    private String tokenHeader;

    public final TokenRepository tokenRepository;

    @Around(value = "@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String accessToken = request.getHeader(atkHeader);
        String refreshToken = request.getHeader(rtkHeader);
        if(accessToken == null){
            throw new BadRequestException(new BaseResponse(BaseStatus.BLANK_ACCESS_TOKEN));
        }
        if(refreshToken == null){
            throw new BadRequestException(new BaseResponse(BaseStatus.BLANK_REFRESH_TOKEN));
        }

        Token token = tokenRepository.findByAtk(accessToken);
        if(token == null){
            throw new BadRequestException(new BaseResponse(BaseStatus.ACCESS_TOKEN_NOT_FOUND));
        }

        if(token.getAtkValidAt().isBefore(LocalDateTime.now())){
            throw new BadRequestException(new BaseResponse(BaseStatus.ACCESS_TOKEN_OUT_DATED));
        }

        request.setAttribute(tokenHeader,token);

        return joinPoint.proceed();
    }
}
