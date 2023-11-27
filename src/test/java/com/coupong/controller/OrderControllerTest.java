package com.coupong.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void 비회원_주문() throws Exception{
        mockMvc.perform(post("/order/guest"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 비회원_주문조회() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/guest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 회원_주문() throws Exception{
        mockMvc.perform(post("/order/member"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원_주문조회() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/member")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}