package com.coupong.controller;

import com.coupong.config.response.BaseResponse;
import com.coupong.member.dto.MemberLoginDto;
import com.coupong.member.dto.MemberSignupDto;
import com.coupong.token.dto.TokenResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${access-token.header-name}")
    private String atkHeader;

    @Value("${refresh-token.header-name}")
    private String rtkHeader;

    @Value("${request-header.token-name}")
    private String tokenHeader;

    private String testEmail = "test@email.com";
    private String testPass = "test pass";
    private String testNick = "test nick";
    private String testPhone = "01012341234";

    @Test
    public void 회원가입() throws Exception{
        String content = objectMapper.writeValueAsString(
                MemberSignupDto.builder()
                        .email(testEmail)
                        .password(testPass)
                        .nickname(testNick)
                        .phoneNumber(testPhone)
                        .build()
        );
        mockMvc.perform(post("/member/sign-up")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인() throws Exception{
        signup();

        String content = objectMapper.writeValueAsString(
                MemberLoginDto.builder()
                        .email(testEmail)
                        .password(testPass)
                        .build()
        );

        mockMvc.perform(post("/member/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인실패() throws Exception{
        signup();
        String content = objectMapper.writeValueAsString(
                MemberLoginDto.builder()
                        .email(testEmail)
                        .password("wrong pass")
                        .build()
        );

        mockMvc.perform(post("/member/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 토큰재발급() throws Exception{
        TokenResponse loginToken = getLoginToken();
        mockMvc.perform(get("/member/reissue")
                        .header(atkHeader, loginToken.getAccessToken())
                        .header(rtkHeader, loginToken.getRefreshToken()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public TokenResponse getLoginToken() throws Exception{
        signup();
        MvcResult loginResult = login();
        BaseResponse resData = objectMapper.readValue(
                loginResult.getResponse().getContentAsString()
                , new TypeReference<BaseResponse>() {
                }
        );

        String at = ((LinkedHashMap<String, String>) resData.getData()).get("accessToken");
        String rt = ((LinkedHashMap<String, String>) resData.getData()).get("refreshToken");

        return new TokenResponse(at, rt);
    }

    public void signup() throws Exception{
        String content = objectMapper.writeValueAsString(
                MemberSignupDto.builder()
                        .email(testEmail)
                        .password(testPass)
                        .nickname(testNick)
                        .phoneNumber(testPhone)
                        .build()
        );
        mockMvc.perform(post("/member/sign-up")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    public MvcResult login() throws Exception{
        String content = objectMapper.writeValueAsString(
                MemberLoginDto.builder()
                        .email(testEmail)
                        .password(testPass)
                        .build()
        );

        return mockMvc.perform(post("/member/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }
}