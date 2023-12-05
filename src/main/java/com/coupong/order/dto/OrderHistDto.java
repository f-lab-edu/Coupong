package com.coupong.order.dto;

import com.coupong.constant.OrderStatus;
import com.coupong.entity.Order;
import com.coupong.entity.OrderItem;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderHistDto {

    private String orderRid;

    private String address;

    private OrderStatus orderStatus;

    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderHistDto(){}

    public OrderHistDto(String orderRid, String address, OrderStatus orderStatus, List<OrderItem> orderItems) {
        this.orderRid = orderRid;
        this.address = address;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public OrderHistDto(Order order) {
        this.orderRid = order.getRid();
        this.address = order.getAddress();
        this.orderStatus = order.getStatus();
    }

    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public String getOrderRid() {
        return orderRid;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
