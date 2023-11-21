package com.coupong.controller;

import com.coupong.member.dto.TokenResponse;
import com.coupong.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/sign-up")
    public void signUp(){
        memberService.signup();
    }

    @PostMapping("/login")
    public TokenResponse login(){
        return memberService.login();
    }
}
