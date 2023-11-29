package com.coupong.coupon.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@ToString
public class CouponDto {

    @NotBlank
    private Long id;

    public CouponDto() {}

    public CouponDto(Long id) {
        this.id = id;
    }
}