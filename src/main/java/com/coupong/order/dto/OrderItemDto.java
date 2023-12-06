package com.coupong.order.dto;

import lombok.Getter;

import java.io.Serializable;

public class OrderItemDto {

    private String itemRid;

    private Long itemOptionId;

    private Integer quantity;

    private Long issuedCouponId;

    public OrderItemDto() {}

    public OrderItemDto(String itemRid, Long itemOptionId, Integer quantity, Long issuedCouponId) {
        this.itemRid = itemRid;
        this.itemOptionId = itemOptionId;
        this.quantity = quantity;
        this.issuedCouponId = issuedCouponId;
    }

    public String getItemRid() {
        return itemRid;
    }

    public Long getItemOptionId() {
        return itemOptionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getIssuedCouponId() {
        return issuedCouponId;
    }
}
