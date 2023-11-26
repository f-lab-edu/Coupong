package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.CouponStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static com.coupong.constant.BaseStatus.*;

@Entity
@Getter
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Integer discountPercent;

    private Integer maxPrice;

    private Integer totalAmount;

    private Integer issuedAmount;

    @Enumerated(EnumType.ORDINAL)
    private CouponStatus status;

    private LocalDateTime applyAt;

    private LocalDateTime expireAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void isValid() throws BadRequestException {
        if(!status.equals(CouponStatus.AVAILABLE)) {
            throw new BadRequestException(new BaseResponse(UNAVAILABLE_COUPON));
        }
        if(this.applyAt.isBefore(LocalDateTime.now())) {
            // 적용 시작 시간이 지나지 않았으면 false
            throw new BadRequestException(new BaseResponse(BEFORE_COUPON_START_TIME));
        }
        if(this.expireAt.isAfter(LocalDateTime.now())) {
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
