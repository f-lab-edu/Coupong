package com.coupong.coupon.service.impl;

import com.coupong.config.exception.NotFoundException;
import com.coupong.coupon.dto.CouponDto;
import com.coupong.coupon.repository.CouponRepository;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    @Transactional
    public IssuedCoupon issueCoupon(Member member, CouponDto couponDto) {
        Optional<Coupon> opCoupon = couponRepository.findById(couponDto.getId());
        Coupon coupon = opCoupon.orElseThrow(NotFoundException::new);

        // 발급할 수 있는 쿠폰인지 확인
        coupon.isValid();

        // 쿠폰 발급 수량 변경
        coupon.issue();
        IssuedCoupon issuedCoupon = IssuedCoupon.createIssuedCoupon(member, coupon);
        return couponRepository.save(issuedCoupon);
    }

    @Override
    @Transactional
    public IssuedCoupon useCoupon(Integer issuedCouponId) {
        Optional<IssuedCoupon> opIssuedCoupon = couponRepository.findIssuedCouponById(issuedCouponId);
        IssuedCoupon issuedCoupon = opIssuedCoupon.orElseThrow(NotFoundException::new);

        // 사용할 수 있는 쿠폰인지 확인하기
        issuedCoupon.canUse();

        // 발급된 쿠폰 상태
        issuedCoupon.use();
        return issuedCoupon;
    }
}
