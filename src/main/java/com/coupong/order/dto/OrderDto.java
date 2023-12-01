package com.coupong.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto {

    private String phoneNumber;

    private String address;

    private List<OrderItemDto> orderItems;

    private Integer issuedCouponId;

    public OrderDto() {}

    public OrderDto(String phoneNumber, String address, List<OrderItemDto> orderItems, Integer issuedCouponId) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
        this.issuedCouponId = issuedCouponId;
    }
}
