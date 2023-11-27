package com.coupong.order.dto;

import com.coupong.constant.OrderStatus;
import com.coupong.entity.Order;
import com.coupong.entity.OrderItem;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class OrderHistDto {

    private String orderRid;

    private String address;

    private OrderStatus orderStatus;

    private List<OrderItem> orderItems;

    public OrderHistDto(){}

    public OrderHistDto(Order order) {
        this.orderRid = order.getRid();
        this.address = order.getAddress();
        this.orderStatus = order.getStatus();
    }

    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
