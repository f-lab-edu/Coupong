package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.CouponKind;
import com.coupong.constant.CouponStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.coupong.constant.BaseStatus.*;

@Entity(name = "coupon")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA를 위해 기본생성자 추가
public class Coupon implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private CouponKind kind;

    @Max(100)
    private Integer discntRate;

    private Integer maxDiscntPrice;

    private Integer minOrderAmt;

    @NotNull
    private Integer totalCnt;

    @ColumnDefault("0")
    private Integer issuedCnt;

    @Enumerated(EnumType.ORDINAL)
    private CouponStatus status;

    private LocalDateTime applyAt;

    private LocalDateTime expireAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj instanceof Coupon) {
            Coupon coupon = (Coupon)obj;
            return this.id.equals(coupon.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Coupon createCoupon(String name, CouponKind kind
            , Integer discntRate, Integer maxDiscntPrice, Integer minOrderAmt, Integer totalCnt
            , LocalDateTime applyAt, LocalDateTime expireAt) {

        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setKind(kind);
        coupon.setDiscntRate(discntRate);
        coupon.setMaxDiscntPrice(maxDiscntPrice);
        coupon.setMinOrderAmt(minOrderAmt);
        coupon.setTotalCnt(totalCnt);
        coupon.setStatus(CouponStatus.AVAILABLE);
        coupon.setApplyAt(applyAt);
        coupon.setExpireAt(expireAt);
        return coupon;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setKind(CouponKind kind) {
        this.kind = kind;
    }

    private void setDiscntRate(Integer discntRate) {
        this.discntRate = discntRate;
    }

    private void setMaxDiscntPrice(Integer maxDiscntPrice) {
        this.maxDiscntPrice = maxDiscntPrice;
    }

    private void setMinOrderAmt(Integer minOrderAmt) {
        this.minOrderAmt = minOrderAmt;
    }

    private void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    private void setStatus(CouponStatus status) {
        this.status = CouponStatus.AVAILABLE;
    }

    private void setApplyAt(LocalDateTime applyAt) {
        this.applyAt = applyAt;
    }

    private void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public void isValid() throws BadRequestException {
        if(!status.equals(CouponStatus.AVAILABLE)) {
            throw new BadRequestException(new BaseResponse(UNAVAILABLE_COUPON));
        }
        if(this.applyAt.isAfter(LocalDateTime.now())) {
            // 적용 시작 시간이 지나지 않았으면 false
            throw new BadRequestException(new BaseResponse(BEFORE_COUPON_START_TIME));
        }
        if(this.expireAt.isBefore(LocalDateTime.now())) {
            // 만료 시간이 지났으면 false
            throw new BadRequestException(new BaseResponse(AFTER_COUPON_END_TIME));
        }
    }

    public void issue() throws BadRequestException {
        if(this.totalCnt.compareTo(this.issuedCnt) < 0) {
            // 총 개수가 발급된 개수보다 같거나 작으면 false
            throw new BadRequestException(new BaseResponse(COUPON_HAS_RUN_OUT));
        }
        this.issuedCnt++;
    }


}