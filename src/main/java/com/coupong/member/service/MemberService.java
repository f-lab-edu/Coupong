package com.coupong.member.service;

import com.coupong.member.dto.MemberLoginDto;
import com.coupong.member.dto.MemberSignupDto;
import com.coupong.token.dto.TokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    public void signup(MemberSignupDto memberSignupDto, HttpServletRequest request);

    public TokenResponse login(MemberLoginDto memberLoginDto, HttpServletRequest request);

    public TokenResponse reissue(HttpServletRequest request);
}
