package com.coupong.member.service.impl;

import com.coupong.config.response.BaseResponse;
import com.coupong.constant.BaseStatus;
import com.coupong.entity.Member;
import com.coupong.config.exception.BadRequestException;
import com.coupong.entity.Role;
import com.coupong.member.dto.MemberSignupDto;
import com.coupong.member.dto.TokenResponse;
import com.coupong.member.repository.MemberRepository;
import com.coupong.member.service.MemberService;
import com.coupong.role.repository.RoleRepository;
import com.coupong.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void signup(MemberSignupDto memberSignupDto, HttpServletRequest request){
        Role userRole = roleRepository.getUserRole();
        Member m = Member.builder()
                .email(memberSignupDto.getEmail())
                .password(memberSignupDto.getPassword())
                .nickname(memberSignupDto.getNickname())
                .ip(Util.getClientIp(request))
                .phoneNumber(memberSignupDto.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .role(userRole)
                .build();
        memberRepository.save(m);
    }

    @Override
    public TokenResponse login() {

        // 오류처리 예시
        throw new BadRequestException(new BaseResponse(BaseStatus.BAD_REQUEST));
//        return new TokenResponse("test_access_token","test_refresh_token");
    }
}
