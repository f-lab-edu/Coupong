package com.coupong.coupon.service;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;

public interface CouponService {

    /**
     * 쿠폰 발급
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

}
