package com.coupong.member.service;

import com.coupong.member.dto.TokenResponse;

public interface MemberService {
    public void signup();

    public TokenResponse login();
}
