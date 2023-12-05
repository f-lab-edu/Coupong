package com.coupong.order.dto;

import com.coupong.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

public class OrderResultDto {

    private String rid;

    private String status;

    private LocalDateTime orderAt;

    public OrderResultDto() {}

    public OrderResultDto(String rid, String status, LocalDateTime orderAt) {
        this.rid = rid;
        this.status = status;
        this.orderAt = orderAt;
    }

    public OrderResultDto(Order order) {
        this.rid = order.getRid();
        this.status = order.getStatus().name();
        this.orderAt = order.getOrderAt();
    }

    public String getRid() {
        return rid;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }
}
