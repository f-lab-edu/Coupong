package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.IssuedCouponStatus;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static com.coupong.constant.BaseStatus.*;

@Entity(name = "issued_coupon")
@Getter
public class IssuedCoupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.ORDINAL)
    private IssuedCouponStatus status;

    private LocalDateTime usedAt;

    @CreationTimestamp
    private LocalDateTime issuedAt;

    @OneToOne(mappedBy = "issuedCoupon")
    private Order order;

    public static IssuedCoupon createIssuedCoupon(Member member, Coupon coupon) {
        IssuedCoupon issuedCoupon = new IssuedCoupon();
        issuedCoupon.setCoupon(coupon);
        issuedCoupon.setMember(member);
        issuedCoupon.setStatus(IssuedCouponStatus.ISSUED);
        issuedCoupon.setIssuedAt(LocalDateTime.now());

        return issuedCoupon;
    }

    public void canUse() throws RuntimeException {
        coupon.isValid(); // 유효한 쿠폰인지 확인

        if(status.equals(IssuedCouponStatus.USED)) {
            throw new BadRequestException(new BaseResponse(COUPON_USED));   // 사용된 상태면 false
        }
    }

    public void use() {
        this.status = IssuedCouponStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    private void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setStatus(IssuedCouponStatus status) {
        this.status = status;
    }

    private void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}