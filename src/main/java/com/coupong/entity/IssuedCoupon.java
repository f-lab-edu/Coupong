package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.IssuedCouponStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.coupong.constant.BaseStatus.*;

@Entity(name = "issued_coupon")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA를 위해 기본생성자 추가
public class IssuedCoupon implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj instanceof IssuedCoupon) {
            IssuedCoupon issuedCoupon = (IssuedCoupon)obj;
            return this.id.equals(issuedCoupon.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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