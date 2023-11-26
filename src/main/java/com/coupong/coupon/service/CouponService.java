package com.coupong.coupon.service;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;

public interface CouponService {

    void issueCoupon(Member member, CouponDto couponDto);

    IssuedCoupon useCoupon(Integer issuedCouponId);

}
