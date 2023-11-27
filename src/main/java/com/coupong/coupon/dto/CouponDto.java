package com.coupong.coupon.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
public class CouponDto {

    @NotBlank
    private int id;

    public CouponDto() {
    }

    public CouponDto(int id) {
        this.id = id;
    }
}