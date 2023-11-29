package com.coupong.coupon.service;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;

import java.time.LocalDateTime;

public interface CouponService {

    /**
     * 멤버에게 쿠폰 발급
     * @param member
     * @param couponDto
     */
    IssuedCoupon issueCoupon(Member member, CouponDto couponDto);

    /**
     * 쿠폰 사용
     * @param issuedCouponId
     * @return
     */
    IssuedCoupon useCoupon(Integer issuedCouponId);

    /**
     * 쿠폰 생성
     * @param name
     * @param discntRate
     * @param maxDiscntPrice
     * @param minOrderAmt
     * @param totalCnt
     * @param applyAt
     * @param expireAt
     * @return
     */
    Coupon addCoupon(String name
            , Integer discntRate, Integer maxDiscntPrice, Integer minOrderAmt, Integer totalCnt
            , LocalDateTime applyAt, LocalDateTime expireAt);

}
