package com.coupong.member.service.impl;

import com.coupong.config.response.BaseResponse;
import com.coupong.constant.BaseStatus;
import com.coupong.entity.Member;
import com.coupong.config.exception.BadRequestException;
import com.coupong.member.dto.TokenResponse;
import com.coupong.member.repository.MemberRepository;
import com.coupong.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void signup(){
        Member tMember = new Member.Builder()
                .email("test@gmail.com")
                .password("abcd")
                .build();
        memberRepository.save(tMember);
    }

    @Override
    public TokenResponse login() {

        // 오류처리 예시
        throw new BadRequestException(new BaseResponse(BaseStatus.BAD_REQUEST));
//        return new TokenResponse("test_access_token","test_refresh_token");
    }
}
