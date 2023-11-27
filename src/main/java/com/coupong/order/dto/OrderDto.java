package com.coupong.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto {

    private String phoneNumber;

    private String address;

    private List<OrderItemDto> orderItems;

    private Integer issuedCouponId;
}
