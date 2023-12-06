package com.coupong.coupon.service.impl;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.exception.NotFoundException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.CouponKind;
import com.coupong.constant.CouponStatus;
import com.coupong.constant.IssuedCouponStatus;
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

import static com.coupong.constant.BaseStatus.*;
import static com.coupong.constant.BaseStatus.AFTER_COUPON_END_TIME;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IssuedCoupon issueCoupon(Member member, CouponDto couponDto) {

        Coupon coupon = couponRepository.findById(couponDto.getId()).orElseThrow(NotFoundException::new);

        // 발급할 수 있는 쿠폰인지 확인
        isValid(coupon);

        // 쿠폰 발급 수량 변경
        if(coupon.getTotalCnt().compareTo(coupon.getIssuedCnt()) <= 0) {
            // 총 개수가 발급된 개수보다 같거나 작으면 false
            throw new BadRequestException(new BaseResponse(COUPON_HAS_RUN_OUT));
        }
        coupon.issue();
        IssuedCoupon issuedCoupon = IssuedCoupon.createIssuedCoupon(member, coupon);
        return couponRepository.save(issuedCoupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public IssuedCoupon useCoupon(Integer issuedCouponId, CouponKind kind) {

        IssuedCoupon issuedCoupon = couponRepository.findIssuedCouponById(issuedCouponId).orElseThrow(NotFoundException::new);

        // 사용할 수 있는 쿠폰인지 확인하기
        canUse(issuedCoupon, kind);

        // 발급된 쿠폰 상태
        issuedCoupon.use();
        return issuedCoupon;
    }

    @Override
    public void isValid(Coupon coupon) {
        if(!coupon.getStatus().equals(CouponStatus.AVAILABLE)) {
            throw new BadRequestException(new BaseResponse(UNAVAILABLE_COUPON));
        }
        if(coupon.getApplyAt().isAfter(LocalDateTime.now())) {
            // 적용 시작 시간이 지나지 않았으면 false
            throw new BadRequestException(new BaseResponse(BEFORE_COUPON_START_TIME));
        }
        if(coupon.getExpireAt().isBefore(LocalDateTime.now())) {
            // 만료 시간이 지났으면 false
            throw new BadRequestException(new BaseResponse(AFTER_COUPON_END_TIME));
        }
    }

    @Override
    public void canUse(IssuedCoupon issuedCoupon, CouponKind kind) {
        isValid(issuedCoupon.getCoupon()); // 유효한 쿠폰인지 확인

        // 쿠폰 종류 확인
        if(!issuedCoupon.getCoupon().getKind().equals(kind)) {
            if(kind.equals(CouponKind.ITEM)) {
                // 주문에는 장바구니 쿠폰을 사용해야 한다.
                throw new BadRequestException(new BaseResponse(HAVE_TO_BASKET_COUPON));   // 사용된 상태면 false
            } else if(kind.equals(CouponKind.BASKET)) {
                // 상품에는 상품 쿠폰을 사용해야 한다.
                throw new BadRequestException(new BaseResponse(HAVE_TO_ITEM_COUPON));   // 사용된 상태면 false
            }
        }

        // 쿠폰 상태 확인
        if(issuedCoupon.getStatus().equals(IssuedCouponStatus.USED)) {
            throw new BadRequestException(new BaseResponse(COUPON_USED));   // 사용된 상태면 false
        }
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
