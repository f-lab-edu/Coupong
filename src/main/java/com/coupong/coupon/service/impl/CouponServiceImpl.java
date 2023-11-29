package com.coupong.coupon.service.impl;

import com.coupong.config.exception.NotFoundException;
import com.coupong.constant.CouponKind;
import com.coupong.coupon.dto.CouponDto;
import com.coupong.coupon.repository.CouponRepository;
import com.coupong.coupon.service.CouponService;
import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IssuedCoupon issueCoupon(Member member, CouponDto couponDto) {

        log.info("**** service ****");

        Optional<Coupon> opCoupon = couponRepository.findById(couponDto.getId());
        Coupon coupon = opCoupon.orElseThrow(NotFoundException::new);

        log.info("coupon: " + coupon.toString());

        // 발급할 수 있는 쿠폰인지 확인
        coupon.isValid();

        // 쿠폰 발급 수량 변경
        coupon.issue();
        IssuedCoupon issuedCoupon = IssuedCoupon.createIssuedCoupon(member, coupon);
        return couponRepository.save(issuedCoupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public IssuedCoupon useCoupon(Integer issuedCouponId) {

        Optional<IssuedCoupon> opIssuedCoupon = couponRepository.findIssuedCouponById(issuedCouponId);
        IssuedCoupon issuedCoupon = opIssuedCoupon.orElseThrow(NotFoundException::new);

        // 사용할 수 있는 쿠폰인지 확인하기
        issuedCoupon.canUse();

        // 발급된 쿠폰 상태
        issuedCoupon.use();
        return issuedCoupon;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Coupon addCoupon(String name
            , Integer discntRate, Integer maxDiscntPrice, Integer minOrderAmt
            , Integer totalCnt, LocalDateTime applyAt, LocalDateTime expireAt) {

        Coupon coupon = Coupon.createCoupon(name, CouponKind.ITEM,
                discntRate, maxDiscntPrice, minOrderAmt,
                totalCnt, applyAt, expireAt);

        return couponRepository.save(coupon);
    }
}
