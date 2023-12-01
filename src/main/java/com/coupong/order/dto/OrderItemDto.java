package com.coupong.order.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OrderItemDto {

    private String itemRid;

    private Long itemOptionId;

    private Integer quantity;

    private Integer issuedCouponId;

    public OrderItemDto() {}

    public OrderItemDto(String itemRid, Long itemOptionId, Integer quantity, Integer issuedCouponId) {
        this.itemRid = itemRid;
        this.itemOptionId = itemOptionId;
        this.quantity = quantity;
        this.issuedCouponId = issuedCouponId;
    }

}
