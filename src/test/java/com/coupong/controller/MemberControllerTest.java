package com.coupong.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    MockMvc mockMvc;

    @Test
    public void 회원가입() throws Exception{
        mockMvc.perform(post("/member/sign-up"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인() throws Exception{
        mockMvc.perform(post("/member/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}