package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.CouponStatus;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static com.coupong.constant.BaseStatus.*;

@Entity(name = "coupon")
@Getter
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Integer discountPercent;

    private Integer maxPrice;

    private Integer totalAmount;

    @ColumnDefault("0")
    private Integer issuedAmount;

    @Enumerated(EnumType.ORDINAL)
    private CouponStatus status;

    private LocalDateTime applyAt;

    private LocalDateTime expireAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Coupon() {}

    public Coupon(int id, String name
            , Integer discountPercent, Integer maxPrice, Integer totalAmount, Integer issuedAmount
            , CouponStatus status, LocalDateTime applyAt, LocalDateTime expireAt) {
        this.id = id;
        this.name = name;
        this.discountPercent = discountPercent;
        this.maxPrice = maxPrice;
        this.totalAmount = totalAmount;
        this.issuedAmount = issuedAmount;
        this.status = status;
        this.applyAt = applyAt;
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
        if(this.totalAmount.compareTo(this.issuedAmount) < 0) {
            // 총 개수가 발급된 개수보다 같거나 작으면 false
            throw new BadRequestException(new BaseResponse(COUPON_HAS_RUN_OUT));
        }
        this.issuedAmount++;
    }
}