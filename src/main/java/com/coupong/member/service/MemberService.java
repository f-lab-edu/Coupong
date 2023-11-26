package com.coupong.member.service;

import com.coupong.member.dto.MemberSignupDto;
import com.coupong.member.dto.TokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    public void signup(MemberSignupDto memberSignupDto, HttpServletRequest request);

    public TokenResponse login();
}
