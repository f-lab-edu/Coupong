package com.coupong.order.dto;

import com.coupong.entity.Order;

import java.time.LocalDateTime;

public class OrderResultDto {

    private String rid;

    private String status;

    private LocalDateTime orderDate;

    public OrderResultDto(Order order) {
        this.rid = order.getRid();
        this.status = order.getStatus().name();
        this.orderDate = order.getOrderDate();
    }
}
