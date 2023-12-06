package com.coupong.controller;

import com.coupong.order.dto.OrderItemDto;
import com.coupong.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private ObjectMapper objectMapper;

    // 테스트 대상 - 사용하는 객체들의 의존성이 주입된다.
    @InjectMocks
    private OrderController target;

    // 테스트 대상에 주입시켜 줄 객체 (테스트 대상에서 사용할 객체)
    @Mock
    private OrderService orderService;

    // HTTP 호출 생성
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 비회원_주문() throws Exception{

        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDto("상품RID", 1L, 1, 3L));

        Map<String, Object> input = new HashMap<>();
        input.put("phoneNumber", "01011111111");
        input.put("address", "주소");
        input.put("orderItems", orderItems);
        input.put("issuedCouponId", 14);

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

        Map<String, Object> input = new HashMap<>();
        input.put("phoneNumber", "01011111111");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/guest")
                        .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 회원_주문() throws Exception{

        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDto("상품RID", 1L, 1, 4L));

        Map<String, Object> input = new HashMap<>();
        input.put("phoneNumber", "01011111111");
        input.put("address", "주소");
        input.put("orderItems", orderItems);
        input.put("issuedCouponId", 14);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/member")
                        .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원_주문조회() throws Exception{

        Map<String, Object> input = new HashMap<>();
        input.put("orderRid", "ORD-");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/member")
                        .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}