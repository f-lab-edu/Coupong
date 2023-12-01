package com.coupong.order.service;

import com.coupong.entity.*;
import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;
import com.coupong.order.dto.OrderResultDto;

import java.util.List;

public interface OrderService {

    List<OrderHistDto> getOrderInfoByGuest(String phoneNumber);

    List<OrderHistDto> getOrderInfoByMember(String orderRid);

    OrderResultDto order(Member member, OrderDto orderDto);

    int getCouponAppAmt(Item item, Coupon coupon);

    int getTotalItemFee(List<OrderItem> orderItems, Coupon coupon);

    void canOrder(Item item, Integer quantity);
}
