package com.coupong.controller;

import com.coupong.order.dto.OrderItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 비회원_주문() throws Exception{
        List<OrderItemDto> orderItems = new ArrayList<>();

        Map<String, Object> input = new HashMap<>();
        input.put("phoneNumber", "01011111111");
        input.put("address", 1);
        input.put("orderItems", orderItems);
        input.put("issuedCouponId", 1);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/guest")
                        .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                        .accept(MediaType.APPLICATION_JSON))
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
        List<OrderItemDto> orderItems = new ArrayList<>();

        Map<String, Object> input = new HashMap<>();
        input.put("phoneNumber", "01011111111");
        input.put("address", 1);
        input.put("orderItems", orderItems);
        input.put("issuedCouponId", 1);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/member")
                        .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}