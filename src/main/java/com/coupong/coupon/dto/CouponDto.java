package com.coupong.coupon.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ToString
public class CouponDto {

    @NotBlank
    private Long id;

    public CouponDto() {}

    public CouponDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String toString() {
        return "CouponDto(" +
                "id=" + this.id +
                ")";
    }
}