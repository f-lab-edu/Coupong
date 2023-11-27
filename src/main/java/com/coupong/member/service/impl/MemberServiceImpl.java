package com.coupong.member.service.impl;

import com.coupong.config.response.BaseResponse;
import com.coupong.constant.BaseStatus;
import com.coupong.entity.Member;
import com.coupong.config.exception.BadRequestException;
import com.coupong.entity.Role;
import com.coupong.entity.Token;
import com.coupong.member.dto.MemberLoginDto;
import com.coupong.member.dto.MemberSignupDto;
import com.coupong.token.dto.TokenResponse;
import com.coupong.member.repository.MemberRepository;
import com.coupong.member.service.MemberService;
import com.coupong.role.repository.RoleRepository;
import com.coupong.token.repository.TokenRepository;
import com.coupong.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final TokenRepository tokenRepository;

    @Value("${request-header.token-name}")
    private String tokenHeader;

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
    @Transactional
    public TokenResponse login(MemberLoginDto memberLoginDto, HttpServletRequest request) {

        Member member = memberRepository.findByEmail(memberLoginDto.getEmail());
        if(member == null){
            throw new BadRequestException(new BaseResponse(BaseStatus.CANNOT_FIND_USER));
        }

        if(!member.getPassword().equals(memberLoginDto.getPassword())){
            throw new BadRequestException(new BaseResponse(BaseStatus.PASSWORD_MISMATCH));
        }

        Token token = createAndSaveToken(member);

        return TokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    @Override
    @Transactional
    public TokenResponse reissue(HttpServletRequest request) {
        Token loginToken = (Token)request.getAttribute(tokenHeader);
        Member member = loginToken.getMember();

        Token newToken = createAndSaveToken(member);

        return TokenResponse.builder()
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }

    public Token createAndSaveToken(Member member) {
        Token token = Token.builder()
                .member(member)
                .accessToken(Util.generateRid())
                .atkValidAt(LocalDateTime.now().plusHours(3))
                .refreshToken(Util.generateRid())
                .rtkValidAt(LocalDateTime.now().plusHours(4))
                .build();

        tokenRepository.save(token);

        return token;
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
        Token loginToken = (Token)request.getAttribute(tokenHeader);
        tokenRepository.delete(loginToken);
    }
}
