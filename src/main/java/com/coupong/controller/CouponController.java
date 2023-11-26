package com.coupong.controller;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/issue")
    public void issueCoupon(CouponDto couponDto) {
        Member member = new Member.Builder()
                .email("test@gmail.com")
                .build();

        couponService.issueCoupon(member, couponDto);
    }

}
