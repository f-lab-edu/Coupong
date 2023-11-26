package com.coupong.order.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phoneNumber;

    private String address;

    private List<OrderItemDto> orderItems;

    private Integer issuedCouponId;
}
