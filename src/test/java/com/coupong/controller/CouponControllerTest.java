package com.coupong.controller;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.Coupon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CouponControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    // 테스트 대상 - 사용하는 객체들의 의존성이 주입된다.
    @InjectMocks
    private CouponController target;

    // 테스트 대상에 주입시켜 줄 객체 (테스트 대상에서 사용할 객체)
    @Mock
    private CouponService couponService;

    // HTTP 호출 생성
    @Autowired
    private MockMvc mockMvc;

    private Long couponId;

    @BeforeEach
    void beforeEach() {
        // 테스트를 시작할 때마다 실행
        //mockMvc = MockMvcBuilders.standaloneSetup(target).build();

//        Coupon coupon = couponService.addCoupon("쿠폰이름", 10, 1000000, 10000, 1000
//                , LocalDateTime.now(), LocalDateTime.of(2024, 1, 1, 0, 0));
//        couponId = coupon.getId();    //coupon 생성이 안됨 (error msg : because "coupon" is null)

        couponId = 1L;
    }

    @Test
    public void 쿠폰_발급() throws Exception{

        Map<String, Object> input = new HashMap<>();
        input.put("id", couponId);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/coupon/issue")
                        .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(input))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}