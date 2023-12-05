package com.coupong.order.dto;

import lombok.Getter;

import java.util.List;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public Integer getIssuedCouponId() {
        return issuedCouponId;
    }
}
