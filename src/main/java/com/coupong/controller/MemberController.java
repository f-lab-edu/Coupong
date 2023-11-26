package com.coupong.controller;

import com.coupong.config.aop.LoginCheck;
import com.coupong.member.dto.MemberLoginDto;
import com.coupong.member.dto.MemberSignupDto;
import com.coupong.token.dto.TokenResponse;
import com.coupong.member.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/sign-up")
    public void signUp(
            @RequestBody @Validated MemberSignupDto memberSignupDto, HttpServletRequest request
    ){
        memberService.signup(memberSignupDto,request);
    }

    @PostMapping("/login")
    public TokenResponse login(
            @RequestBody @Validated MemberLoginDto memberLoginDto, HttpServletRequest request
    ){
        return memberService.login(memberLoginDto,request);
    }

    @LoginCheck
    @GetMapping("/reissue")
    public TokenResponse reissue(HttpServletRequest request){
        return memberService.reissue(request);
    }
}
