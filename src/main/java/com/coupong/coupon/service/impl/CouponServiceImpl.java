package com.coupong.coupon.service.impl;

import com.coupong.coupon.dto.CouponDto;
import com.coupong.coupon.repository.CouponRepository;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void issueCoupon(Member member, CouponDto couponDto) throws RuntimeException {
        Coupon coupon = couponRepository.findById(couponDto.getId());
        coupon.isValid();

        // 쿠폰 발급 수량 변경
        coupon.issue();
        IssuedCoupon issuedCoupon = IssuedCoupon.createIssuedCoupon(member.getId(), coupon);
        couponRepository.save(issuedCoupon);
    }

    @Override
    public IssuedCoupon useCoupon(Integer issuedCouponId) throws RuntimeException {
        IssuedCoupon issuedCoupon = couponRepository.findIssuedCouponById(issuedCouponId);
        issuedCoupon.canUse();

        // 발급된 쿠폰 상태 바꾸기
        issuedCoupon.use();
        return issuedCoupon;
    }
}
