package com.coupong.order.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OrderItemDto {

    private String itemRid;

    private Long itemOptionId;

    private Integer quantity;

    private Integer issuedCouponId;

}
