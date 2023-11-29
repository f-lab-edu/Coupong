package com.coupong.controller;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;
import com.coupong.entity.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * 쿠폰 발급
     * @param couponDto 발급할 쿠폰 ID
     */
    @PostMapping("/issue")
    public void issueCoupon(@RequestBody CouponDto couponDto) {

        // 현재 로그인된 멤버 정보 가져오기
        Member member = new Member(1L, "user@gmail.com"
                , "password", "nickname", "127.0.0.0", "01012341234"
                , LocalDateTime.now(), LocalDateTime.now(), null, new Role(0L, "GUEST"));

        couponService.issueCoupon(member, couponDto);
    }

}